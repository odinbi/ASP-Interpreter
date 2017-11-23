package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }


    @Override
    protected String typeName() {
        return "int";
    }


    @Override
    public String toString() {
        return Double.toString(floatValue);
    }


    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }


    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        double temp;
        if (v instanceof RuntimeIntValue ) {
            temp = floatValue + v.getIntValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue + v.getFloatValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        }
        runtimeError("'+' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        double temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue / v.getIntValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue / v.getFloatValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        }
        runtimeError("'/' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue == (double)v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue == v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'==' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue > (double)v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue > v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'>' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue >= (double)v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue >= v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'>=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        double temp;
        if (v instanceof RuntimeIntValue) {
            temp = Math.floor(floatValue/v.getIntValue(v.toString(), where));
            return new RuntimeFloatValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = Math.floor(floatValue/v.getFloatValue(v.toString(), where));
            return new RuntimeFloatValue(temp);
        }
        runtimeError("'//' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue < (double)v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue < v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'<' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue <= (double)v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue <= v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'<=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        double temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue - v.getIntValue(v.toString(), where)
                    * Math.floor(floatValue/v.getIntValue(v.toString(), where));
            return new RuntimeFloatValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue - v.getFloatValue(v.toString(), where)
                  * Math.floor(floatValue/v.getFloatValue(v.toString(), where));
            return new RuntimeFloatValue(temp);
        }
        runtimeError("'%' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        double temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue * v.getIntValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue * v.getFloatValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        }
        runtimeError("'*' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        double temp = floatValue * -1;
        return new RuntimeFloatValue(temp);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return this;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        double temp;
        if (v instanceof RuntimeIntValue) {
            temp = floatValue - v.getIntValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        } else if (v instanceof RuntimeFloatValue){
            temp = floatValue - v.getFloatValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
        }
        runtimeError("'-' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if(getBoolValue(toString(), where)) return new RuntimeBoolValue(false);
        if(!getBoolValue(toString(), where)) return new RuntimeBoolValue(true);
        runtimeError("'not' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    public RuntimeValue evalAnd(RuntimeValue v, AspSyntax where) {
        if(!getBoolValue(toString(), where)) return new RuntimeBoolValue(false);
        if(v.getBoolValue(v.toString(), where)) return v;
        return new RuntimeBoolValue(false);
    }

    public RuntimeValue evalOr(RuntimeValue v, AspSyntax where) {
        if(getBoolValue(toString(), where)) return this;
        if(v.getBoolValue(v.toString(), where)) return v;
        return new RuntimeBoolValue(false);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(floatValue == 0.0) return false;
        if(floatValue != 0.0) return true;
        runtimeError("Type error: "+what+" is not a Boolean!", where);
    	return false;  // Required by the compiler!
    }
}
