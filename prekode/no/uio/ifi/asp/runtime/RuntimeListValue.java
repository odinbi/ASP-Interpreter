package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> lst = new ArrayList<>();

    public RuntimeListValue(RuntimeValue value) {
        lst.add(value);
    }


    @Override
    protected String typeName() {
        return "list";
    }


    @Override
    public String toString() {
        String temp = "[";
        boolean count = false;
        for(RuntimeValue rv : lst){
            if(count) temp += ", ";
            temp += rv.toString();
            if(!count) count = true;
        }
        temp += "]"
        return temp;
    }


    public RuntimeValue getEntry(int i){
        return lst.get(i);
    }

    public RuntimeListValue getList(RuntimeValue name) {
        return this;
    }

    @Override
    public RuntimeListValue evalAdd(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            RuntimeValue[] lstArray = ArrayList.toArray(lst);
            lst.clear();
            for(int i = 0; i < v.getIntValue; i++){
                for(RuntimeValue rv : lstArray){
                    lst.add(rv);
                }
            }
            return this;
        }
        runtimeError("'+' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(lst != null);
        }
        runtimeError("'==' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if(!lst.isEmpty()) return new RuntimeBoolValue(false);
        if(lst.isEmpty()) return new RuntimeBoolValue(true);
        runtimeError("'not' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    public RuntimeValue evalAnd(RuntimeValue v, AspSyntax where) {
        if(lst.isEmpty()) return new RuntimeBoolValue(false);
        if(v.getBoolValue(v.toString(), where)) return v;
        else return new RuntimeBoolValue(false);
        runtimeError("'not' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    public RuntimeValue evalOr(RuntimeValue v, AspSyntax where) {
        if(!lst.isEmpty()) return this;
        if(v.getBoolValue(v.toString(), where)) return v;
        else return new RuntimeBoolValue(false);
        runtimeError("'not' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(lst.isEmpty()) return false;
        if(!lst.isEmpty()) return true;
        runtimeError("Type error: "+what+" is not a Boolean!", where);
    	return false;  // Required by the compiler!
    }
}
