package no.uio.ifi.asp.runtime;

import java.util.HashMap;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeScope {
    RuntimeScope outer;
    HashMap<String,RuntimeValue> decls = new HashMap<>();

    public RuntimeScope() {
        outer = null;
    }


    public RuntimeScope(RuntimeScope oScope) {
        outer = oScope;
    }


    public void assign(String id, RuntimeValue val) {
        decls.put(id, val);
    }


    public RuntimeValue find(String id, AspSyntax where) {
        RuntimeValue v = decls.get(id);
        if (v != null){
            return v;
        } else if (outer != null){
            return outer.find(id, where);
        } else{
            RuntimeValue.runtimeError("Name " + id + " not defined!", where);
            return null;  // Required by the compiler.
        }
    }
}
