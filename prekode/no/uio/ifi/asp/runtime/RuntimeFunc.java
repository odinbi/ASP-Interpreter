package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.AspFuncDef;
import java.util.ArrayList;

public class RuntimeFunc extends RuntimeValue{

    AspFuncDef def;
    RuntimeScope defScope;
    String name;

    public RuntimeFunc(String name){
        this.name = name;
    }

    public RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String name){
        this.def = def; this.defScope = defScope; this.name = name;
    }

    public String toString(){
        String retString = "function: " + name;
        return retString;
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> args, RuntimeScope scope, AspSyntax where){
        RuntimeScope defScope = scope;
        RuntimeScope newScope = new RuntimeScope(defScope);
        //checks that number of arguments given matches number expected
        if(def.getNameSize() != args.size()){
            runtimeError("illegal number of arguments to "+typeName()+" " + name + "!", where);
            return null;
        }
        //assigns variables in the new scope
        for(int i = 0; i < args.size(); i++){
            newScope.assign(def.getNameValue(i), args.get(i));
        }

        try{
            def.getSuite().eval(newScope);
        }catch(RuntimeReturnValue rrv){
            return rrv.getValue();
        }
        return null;
    }

    @Override
    public String typeName(){
        return "function";
    }
}
