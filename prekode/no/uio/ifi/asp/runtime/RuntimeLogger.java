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
        System.out.println(tabs + "<" + nonTerm + ">");
        ++evalLevel;
        updateTabs;
    }

    public void leaveEval(String nonTerm){
        System.out.println(tabs + "</" + nonTerm + ">");
        --evalLevel;
        updateTabs;
    }

    public void enterMessage(String message){
        System.out.println(tabs + "     " + message);
    }

    public void updateTabs(){
        tabs = "";
        for(int i = 0; i <= evalLevel; i++){
            tabs += "    ";
        }
    }
}
