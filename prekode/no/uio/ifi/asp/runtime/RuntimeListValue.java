package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> lst = new ArrayList<>();

    public RuntimeListValue(RuntimeValue value) {
        lst.add(value);
    }

    public RuntimeListValue() {
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
        temp += "]";
        return temp;
    }

    public void evalAppend(RuntimeValue value){
        lst.add(value);
    }

     public void evalAssignElem(RuntimeValue indx, RuntimeValue val, AspSyntax where){
         if(indx instanceof RuntimeIntValue || indx instanceof RuntimeFloatValue){
             int index = (int)indx.getIntValue(indx.toString(), where);
             if(index >= lst.size()){
                 evalSubscription(indx, where);
             }
             lst.set(index, val);
             return;
         }
         runtimeError("Illegal index value, expected an integer or float, got: " + indx.toString() + "!", where);
         return;
  }

    public RuntimeValue evalSubscription(RuntimeValue indx, AspSyntax where){
        if(indx instanceof RuntimeIntValue || indx instanceof RuntimeFloatValue){
            int index = (int)indx.getIntValue(indx.toString(), where);
            if(index < lst.size()){
                return lst.get(index);

            }
            for(long i = lst.size(); i <= index; i++){
                lst.add(new RuntimeNoneValue());
            }
            return null;
        }
        runtimeError("Illegal index value, expected an integer or float, got: " + indx.toString() + "!", where);
        return null;
    }

    public RuntimeValue getEntry(int i){
        return lst.get(i);
    }

    @Override
    public RuntimeValue getEntry(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return lst.get((int)v.getIntValue(v.toString(), where));
        }
        runtimeError("getEntry undefined for " + v.typeName() + "!", where);
        return null;
    }

    public RuntimeListValue getList(RuntimeValue name) {
        return this;
    }

    @Override
    public RuntimeListValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            RuntimeListValue temp = new RuntimeListValue();
            for(int i = 0; i < v.getIntValue(v.toString(), where); i++){
                for(int j = 0; j < lst.size(); j++){
                    temp.evalAppend(lst.get(j));
                }
            }
            return temp;
        }
        runtimeError("'*' undefined for "+typeName()+"!", where);
    	return null;
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
        return new RuntimeBoolValue(false);
    }

    public RuntimeValue evalOr(RuntimeValue v, AspSyntax where) {
        if(!lst.isEmpty()) return this;
        if(v.getBoolValue(v.toString(), where)) return v;
        return new RuntimeBoolValue(false);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(lst.isEmpty()) return false;
        if(!lst.isEmpty()) return true;
        runtimeError("Type error: "+what+" is not a Boolean!", where);
    	return false;  // Required by the compiler!
    }
}
