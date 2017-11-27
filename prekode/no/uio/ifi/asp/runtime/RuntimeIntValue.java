package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }


    @Override
    protected String typeName() {
        return "int";
    }


    @Override
    public String toString() {
        return Long.toString(intValue);
    }


    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    public double getFloatValue(String what, AspSyntax where){
        return (double) intValue;
    }


    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            long temp = intValue + v.getIntValue(v.toString(), where);
            return new RuntimeIntValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            double temp = v.getFloatValue(v.toString(), where) + (double)intValue;
            return new RuntimeFloatValue(temp);
    	}
        runtimeError("'+' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        double temp;
        if (v instanceof RuntimeIntValue) {
            temp = intValue / v.getIntValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            temp = (double) intValue / v.getFloatValue(v.toString(), where);
            return new RuntimeFloatValue(temp);
    	}
        runtimeError("'/' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = intValue == v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            temp = (double) intValue == v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	}
        runtimeError("'==' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = intValue > v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            temp = (double) intValue > v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	}
        runtimeError("'>' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = intValue >= v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            temp = (double) intValue >= v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	}
        runtimeError("'>=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            long temp = (long)Math.floor(intValue/v.getIntValue(v.toString(), where));
            return new RuntimeIntValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            double temp = Math.floor(intValue/v.getIntValue(v.toString(), where));
            return new RuntimeFloatValue(temp);
    	}
        runtimeError("'//' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = intValue < v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            temp = (double) intValue < v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	}
        runtimeError("'<' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        boolean temp;
        if (v instanceof RuntimeIntValue) {
            temp = intValue <= v.getIntValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            temp = (double) intValue <= v.getFloatValue(v.toString(), where);
            return new RuntimeBoolValue(temp);
    	}
        runtimeError("'<=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            long temp = Math.floorMod(intValue, v.getIntValue(v.toString(), where));
            return new RuntimeIntValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            double temp = intValue - v.getFloatValue(v.toString(), where)
                    * Math.floor(intValue/v.getFloatValue(v.toString(), where));
            return new RuntimeFloatValue(temp);
    	}
        runtimeError("'%' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            long temp = intValue * v.getIntValue(v.toString(), where);
            return new RuntimeIntValue(temp);
    	} else if (v instanceof RuntimeFloatValue){
            double temp = v.getFloatValue(v.toString(), where) * (double) intValue;
            return new RuntimeFloatValue(temp);
    	}
        runtimeError("'*' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        long temp = intValue * -1;
        return new RuntimeIntValue(temp);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntValue(intValue);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            long temp = intValue - v.getIntValue(v.toString(), where);
            return this;
    	} else if (v instanceof RuntimeFloatValue){
            double temp = v.getFloatValue(v.toString(), where) - (double)intValue;
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
        if(intValue == 0) return false;
        if(intValue != 0) return true;
        runtimeError("Type error: "+what+" is not a Boolean!", where);
    	return false;  // Required by the compiler!
    }
}
