package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.RuntimeFunc;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        // len
        assign("len", new RuntimeFunc("len") {
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> params, AspSyntax where) {
                checkNumParams(params, 1, "len", where);
                return params.get(0).evalLen(where);
            }});
        //float
        assign("float", new RuntimeFunc("float"){
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, AspSyntax where){
                checkNumericValue(param.get(0), "float", where);
                return new RuntimeFloatValue(param.get(0).getFloatValue(param.get(0).toString(), where));
            }
        });
        //int
        assign("int", new RuntimeFunc("int"){
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, AspSyntax where){
                checkNumericValue(param.get(0), "int", where);
                return new RuntimeIntValue(param.get(0).getIntValue(param.get(0).toString(), where));
            }
        });
        //str
        assign("str", new RuntimeFunc("str"){
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, AspSyntax where){
                return new RuntimeStringValue(param.get(0).toString());
            }
        });
        //input
        assign("input", new RuntimeFunc("input"){
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> param, AspSyntax where){
                if(!(param.get(0) instanceof RuntimeStringValue)){
                    RuntimeValue.runtimeError("Illegal paramter, needs to be of type string, was of type " + param.get(0).typeName()+"!", where);
                }
                System.out.print(param.get(0).toString());
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });
        //print
        assign("print", new RuntimeFunc("print"){
            public void evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
                for(RuntimeValue param : actualParams){
                    System.out.print(param.toString() + " ");
                }
                System.out.print("\n");
            }
        });
    }

    private void checkNumericValue(RuntimeValue arg, String id, AspSyntax where){
        if(!(arg instanceof RuntimeStringValue || arg instanceof RuntimeIntValue || arg instanceof RuntimeFloatValue))
            RuntimeValue.runtimeError("Cannot convert parameter type " + arg.typeName() + "to " + id + "!", where);
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
    	if (actArgs.size() != nCorrect)
    	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
