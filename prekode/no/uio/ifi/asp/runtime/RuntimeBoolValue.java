package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeBoolValue extends RuntimeValue {
    boolean boolValue;

    public RuntimeBoolValue(boolean v) {
        boolValue = v;
    }


    @Override
    protected String typeName() {
        return "boolean";
    }


    @Override
    public String toString() {
        return (boolValue ? "True" : "False");
    }


    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return boolValue;
    }

    public RuntimeValue evalAnd(RuntimeValue v, AspSyntax where) {
        if(!getBoolValue(toString(), where)) return new RuntimeBoolValue(false);
        if(v.getBoolValue(v.toString(), where)) return v;
        return new RuntimeBoolValue(false);
    }

    public RuntimeValue evalOr(RuntimeValue v, AspSyntax where){
        if(getBoolValue(toString(), where)) return this;
        if(v.getBoolValue(v.toString(), where)) return v;
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        } else {
            return new RuntimeBoolValue(
            boolValue == v.getBoolValue("== operand",where));
        }
    }


    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(! boolValue);
    }


    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        } else {
            return new RuntimeBoolValue(
            boolValue != v.getBoolValue("!= operand",where));
        }
    }
}
