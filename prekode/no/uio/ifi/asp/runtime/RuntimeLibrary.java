package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        // len
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }});
        //float
        assign("float", new RuntimeFunc("float"){
            @Override
            public RuntimeValue evalFuncCall(RuntimeValue actualParam, AspSyntax where){
                checkNumericValue(actualParam, "float", where);
                return actualParam.getFloatValue();
            }
        });
        //int
        assign("int", new RuntimeFunc("int"){
            @Override
            public RuntimeValue evalFuncCall(RuntimeValue param, AspSyntax where){
                checkNumericValue(actualParam, "int", where);
                return actualParam.getIntValue();
            }
        });
        //str
        assign("str", new RuntimeFunc("str"){
            @Override
            public RuntimeValue evalFuncCall(RuntimeValue param, AspSyntax where){
                return new RuntimeStringValue(param.toString());
            }
        });
        //input
        assign("input", new RuntimeFunc("input"){
            @Override
            public RuntimeValue evalFuncCall(RuntimeValue param, AspSyntax where){
                if(!param instanceof RuntimeStringValue){
                    RuntimeValue.runtimeError("Illegal paramter, needs to be of type string, was of type " + param.typeName()+"!", where);
                }
                System.out.print(param.toString());
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });
        //print
        assign("print", new RuntimeFunc("print"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
                for(RuntimeValue param : actualParams){
                    System.out.print(param.toString() + " ");
                }
                System.out.print("\n");
            }
        });
    }

    private void checkNumericValue(ArrayList<RuntimeValue> arg, String id, AspSyntax where){
        if(!(arg instanceof RuntimeStringValue || arg instanceof RuntimeIntValue || arg instanceof RuntimeFloatValue))
            RuntimeValue.runtimeError("Cannot convert parameter type " + arg.typeName() + "to " + id + "!", where);
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
    	if (actArgs.size() != nCorrect)
    	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
