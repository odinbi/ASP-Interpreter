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
        RuntimeValue returnVal = null;
        RuntimeScope newScope = new RuntimeScope(scope);

        if(def.getNameSize() != args.size()){
            runtimeError("illegal number of arguments to "+typeName()+" " + name + "!", where);
            return null;
        }
        for(int i = 0; i < args.size(); i++){
            newScope.assign(def.getNameValue(i), args.get(i));
        }
        try{
            def.getSuite().eval(newScope);
        }catch(RuntimeReturnValue rrv){
            returnVal = rrv.getValue();
        }
        return returnVal;
    }

    @Override
    public String typeName(){
        return "function";
    }
}
