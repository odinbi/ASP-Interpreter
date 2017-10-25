package no.uio.ifi.asp.main;

import no.uio.ifi.asp.parser.*;

public class RuntimeLogger {
    public boolean doLogEval = false;
    private int evalLevel = 0;
    String tabs = "";

    public RuntimeLogger(boolean state){
        doLogEval = state;
    }

    public void enterEval(String nonTerm){
        if(!doLogEval) return;
        ++evalLevel;
        updateTabs();
        System.out.println(tabs + "<" + nonTerm + ">");
    }

    public void leaveEval(String nonTerm){
        if(!doLogEval) return;
        System.out.println(tabs + "</" + nonTerm + ">");
        --evalLevel;
        updateTabs();
    }

    public void enterMessage(String message){
        if(!doLogEval) return;
        System.out.println(tabs + "     " + message);
    }

    public void updateTabs(){
        tabs = "";
        for(int i = 0; i <= evalLevel; i++){
            tabs += "    ";
        }
    }
}
