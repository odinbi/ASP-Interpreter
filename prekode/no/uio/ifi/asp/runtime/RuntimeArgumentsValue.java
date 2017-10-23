package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.util.ArrayList;

public class RuntimeArgumentsValue extends RuntimeValue {
    ArrayList<RuntimeValue> lst = new ArrayList<>();

    public RuntimeArgumentsValue(RuntimeValue value) {
        lst.add(value);
    }

    public RuntimeArgumentsValue(){
    }

    public void add(RuntimeValue value){
        lst.add(value);
    }

    @Override
    protected String typeName() {
        return "list";
    }


    @Override
    public String toString() {
        String temp = "(";
        boolean count = false;
        for(RuntimeValue rv : lst){
            if(count) temp += ", ";
            temp += rv.toString();
            if(!count) count = true;
        }
        temp += ")";
        return temp;
    }


    public RuntimeValue getEntry(int i){
        return lst.get(i);
    }

    public RuntimeArgumentsValue getList(RuntimeValue name) {
        return this;
    }
}
