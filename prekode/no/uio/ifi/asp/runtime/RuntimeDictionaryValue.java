package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class RuntimeDictionaryValue extends RuntimeValue {
    HashMap<RuntimeValue, RuntimeValue> varMap = new HashMap<>();

    public RuntimeDictionaryValue(RuntimeValue key, RuntimeValue value) {
        varMap.put(key, value);
    }


    @Override
    protected String typeName() {
        return "dictionary";
    }


    @Override
    public String toString() {
        String temp = "{";
        Set varSet = hmap.entrySet();
        Iterator varIterator = varSet.iterator();
        boolean count = false;
        while(varIterator.hasNext()){
            if(count) temp += ", ";
            Map.Entry entries = (Map.Entry)varIterator.next();
            temp += entries.getKey().toString() + ": "
                    + entries.getValue().toString();
            if(!count) count = true;
        }
        temp += "}"
        return temp;
    }

    public RuntimeDictionaryValue getDictionary(){
        return this;
    }

    public RuntimeValue getVariable(RuntimeValue name) {
        return varMap.get(name);
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
        else return new RuntimeBoolValue(false);
        runtimeError("'not' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    public RuntimeValue evalOr(RuntimeValue v, AspSyntax where) {
        if(!varMap.isEmpty()) return this;
        if(v.getBoolValue(v.toString(), where)) return v;
        else return new RuntimeBoolValue(false);
        runtimeError("'not' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(varMap.isEmpty()) return false;
        if(!varMap.isEmpty()) return true;
        runtimeError("Type error: "+what+" is not a Boolean!", where);
    	return false;  // Required by the compiler!
    }

}
