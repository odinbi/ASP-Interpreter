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
        if(def != null){
            if(def.getNameSize() != args.size()){
                runtimeError("illegal number of arguments to "+typeName()+" " + name + "!", where);
                return null;
            }
            RuntimeScope newScope = new RuntimeScope(scope);;
            for(int i = 0; i < args.size(); i++){
                newScope.assign(def.getNameValue(i), args.get(i));
            }
            RuntimeValue returnVal;
            try{
                returnVal = runFunction(newScope);
            }catch(RuntimeReturnValue rrv){
                returnVal = rrv.value;
            }
            return returnVal;
        }
        return null;
    }

    public RuntimeValue runFunction(RuntimeScope scope) throws RuntimeReturnValue{
        return def.getSuite().eval(scope);
    }
    @Override
    public String typeName(){
        return "function";
    }
}
