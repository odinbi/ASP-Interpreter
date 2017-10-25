package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeOperatorValue extends RuntimeValue {
    String value;

    public RuntimeOperatorValue(String v){
        value = v;
    }

    public String getOpr(){
        return value;
    }

    @Override
    protected String typeName(){
        return "operator";
    }
}
