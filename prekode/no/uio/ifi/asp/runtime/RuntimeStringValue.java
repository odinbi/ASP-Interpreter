package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }


    @Override
    protected String typeName() {
        return "string";
    }

    @Override
    public long getIntValue(String what, AspSyntax where){
        long i = 0;
        try{
            i = Long.parseLong(stringValue);
        }catch(NumberFormatException nfe){
            runtimeError("could not convert string: " + stringValue + " to integer!", where);
        }
        return i;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public RuntimeStringValue getEntry(RuntimeValue v, AspSyntax where){
        char[] str = stringValue.toCharArray();
        if(v instanceof RuntimeIntValue){
            return new RuntimeStringValue("" + str[(int)v.getIntValue(v.toString(), where)]);
        }
        runtimeError("getEntry(type) undefined for type" + v.typeName() + "!", where);
        return null;
    }


    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            stringValue += v.getStringValue(v.toString(), where);
            return this;
        }
        runtimeError("'+' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }


    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.equals(v.getStringValue(v.toString(), where)));
        }
        runtimeError("'==' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(!stringValue.equals(v.getStringValue(v.toString(), where)));
        }
        runtimeError("'!=' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            char[] v1 = stringValue.toCharArray();
            char[] v2 = v.getStringValue(v.toString(), where).toCharArray();

            boolean temp = false;
            for (int i = 0; i < v1.length; i++){
                if(i > v2.length){
                    temp = true; break;
                } else if (v1[i] > v2[i]){
                    temp = true; break;
                } else if (v1[i] < v2[i]){
                    break;
                }
            }
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'>' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            char[] v1 = stringValue.toCharArray();
            char[] v2 = v.getStringValue(v.toString(), where).toCharArray();

            boolean temp = true;
            for (int i = 0; i < v1.length; i++){
                if(i > v2.length){
                    break;
                } else if (v1[i] > v2[i]){
                    break;
                } else if (v1[i] < v2[i]){
                    temp = false; break;
                }
            }
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'>=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }


    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            char[] v1 = stringValue.toCharArray();
            char[] v2 = v.getStringValue(v.toString(), where).toCharArray();

            boolean temp = false;
            for (int i = 0; i < v1.length; i++){
                if(i > v2.length){
                    break;
                } else if (v1[i] < v2[i]){
                    temp = true; break;
                } else if (v1[i] > v2[i]){
                    break;
                }
            }
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'<' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            char[] v1 = stringValue.toCharArray();
            char[] v2 = v.getStringValue(v.toString(), where).toCharArray();
            boolean temp = true;
            for (int i = 0; i < v1.length; i++){
                if(i > v2.length){
                    temp = false; break;
                } else if (v1[i] < v2[i]){
                    break;
                } else if (v1[i] > v2[i]){
                    temp = false; break;
                }
            }
            return new RuntimeBoolValue(temp);
        }
        runtimeError("'<=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        String returnString = "";
        if (v instanceof RuntimeIntValue) {
            long count = v.getIntValue(v.toString(), where);
            if(count <= 0){
                return new RuntimeStringValue(returnString);
            } else {
                for(int i = 0; i < count; i++){
                    returnString += stringValue;
                }
            }
            return new RuntimeStringValue(returnString);
        }
        runtimeError("'*' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(stringValue == null || stringValue == "");
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
        if(stringValue == null || stringValue == "") return false;
        return true;
    }

    public String toString(){
        return stringValue;
    }
}
