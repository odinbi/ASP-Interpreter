package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.AspFuncDef;

public class RuntimeFunc extends RuntimeValue{

    AspFuncDef def;
    RuntimeScope defScope;
    String name;

    RuntimeFunc(String name){
        this.name = name;
    }

    RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String name){
        this.def = def; this.defScope = defScope; this.name = name;
    }

    public RuntimeValue evalFuncCall(RuntimeArgumentsValue args, AspSyntax where){
        if(def.name.size() != args.evalLen()){
            runtimeError("illegal number of arguments to "+typeName()+" " + name + "!", where);
            return null;
        }
        RuntimeScope newScope = new RuntimeScope(defScope);
        for(int i = 0; i < args.evalLen(); i++){
            newScope.assign(def.name.get(i).value, args.getEntry(i));
        }
        return runFunction(newScope);
    }

    public RuntimeValue runFunction(RuntimeScope scope){
        return def.suite.eval(scope);
    }

    public String typeName(){
        return "function";
    }
}
