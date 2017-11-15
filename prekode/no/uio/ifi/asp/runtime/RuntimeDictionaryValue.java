package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class RuntimeDictionaryValue extends RuntimeValue {
    HashMap<String, RuntimeValue> varMap = new HashMap<>();

    public RuntimeDictionaryValue(String key, RuntimeValue value) {
        varMap.put(key, value);
    }

    public void addEntry(String key, RuntimeValue value){
        varMap.put(key, value);
    }

    public RuntimeValue evalSubscription(RuntimeValue val, AspSyntax where){
        return getEntry(val, where);
    }

    @Override
    protected String typeName() {
        return "dictionary";
    }


    @Override
    public String toString() {
        String temp = "{";
        Set varSet = varMap.entrySet();
        Iterator varIterator = varSet.iterator();
        boolean count = false;
        while(varIterator.hasNext()){
            if(count) temp += ", ";
            Map.Entry entries = (Map.Entry)varIterator.next();
            temp += entries.getKey().toString() + ": "
                    + entries.getValue().toString();
            if(!count) count = true;
        }
        temp += "}";
        return temp;
    }

    public RuntimeDictionaryValue getDictionary(){
        return this;
    }

    @Override
    public RuntimeValue getEntry(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return varMap.get(v.getStringValue(v.toString(), where));
        }
        runtimeError("getEntry undefined for" + v.typeName() + "!", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(varMap != null);
        }
        runtimeError("'==' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if(!varMap.isEmpty()) return new RuntimeBoolValue(false);
        if(varMap.isEmpty()) return new RuntimeBoolValue(true);
        runtimeError("'not' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    public RuntimeValue evalAnd(RuntimeValue v, AspSyntax where) {
        if(varMap.isEmpty()) return new RuntimeBoolValue(false);
        if(v.getBoolValue(v.toString(), where)) return v;
        return new RuntimeBoolValue(false);
    }

    public RuntimeValue evalOr(RuntimeValue v, AspSyntax where) {
        if(!varMap.isEmpty()) return this;
        if(v.getBoolValue(v.toString(), where)) return v;
        return new RuntimeBoolValue(false);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(varMap.isEmpty()) return false;
        if(!varMap.isEmpty()) return true;
        runtimeError("Type error: "+what+" is not a Boolean!", where);
    	return false;  // Required by the compiler!
    }

}
