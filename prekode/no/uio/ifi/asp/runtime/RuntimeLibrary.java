package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.runtime.RuntimeFunc;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        // len
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> params, RuntimeScope scope, AspSyntax where) {
                checkNumParams(params, 1, "len", where);
                return params.get(0).evalLen(where);
            }});
        //float
        assign("float", new RuntimeFunc("float"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, RuntimeScope scope, AspSyntax where){
                checkNumericValue(param.get(0), "float", where);
                return new RuntimeFloatValue(param.get(0).getFloatValue(param.get(0).toString(), where));
            }
        });
        //int
        assign("int", new RuntimeFunc("int"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, RuntimeScope scope, AspSyntax where){
                checkNumericValue(param.get(0), "int", where);
                return new RuntimeIntValue(param.get(0).getIntValue(param.get(0).toString(), where));
            }
        });
        //str
        assign("str", new RuntimeFunc("str"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, RuntimeScope scope, AspSyntax where){
                return new RuntimeStringValue(param.get(0).toString());
            }
        });
        //input
        assign("input", new RuntimeFunc("input"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, RuntimeScope scope, AspSyntax where){
                if(!(param.get(0) instanceof RuntimeStringValue)){
                    RuntimeValue.runtimeError("Illegal paramter, needs to be of type string, was of type " + param.get(0).typeName()+"!", where);
                }
                System.out.print(param.get(0).toString());
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });
        //print
        assign("print", new RuntimeFunc("print"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, RuntimeScope scope, AspSyntax where){
                for(RuntimeValue param : actualParams){
                    System.out.print(param.toString() + " ");
                }
                System.out.print("\n");
                return new RuntimeNoneValue();
            }
        });
    }

    private void checkNumericValue(RuntimeValue arg, String id, AspSyntax where){
        if(!(arg instanceof RuntimeStringValue || arg instanceof RuntimeIntValue || arg instanceof RuntimeFloatValue))
            RuntimeValue.runtimeError("Cannot convert parameter type " + arg.typeName() + " to " + id + "!", where);
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
    	if (actArgs.size() != nCorrect)
    	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
