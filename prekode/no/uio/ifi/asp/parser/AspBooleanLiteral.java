package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspBooleanLiteral extends AspAtom {
    public boolean value;
    AspBooleanLiteral(int n, boolean bool){
        this.super(n);
        this.value = bool;
    }

    static AspBooleanLiteral parse(Scanner s){
        Main.log.enterParser("boolean litteral");
        if(!s.isBooleanToken()){
            parserError("Expected a booleanToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        if(s.getTokenKind() == trueToken){
            AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum(), true);
        } else{
            AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum(), false);
        }
        Main.log.leaveParser("boolean litteral");
        return abl;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite(" " + value + " ");
    }
}
