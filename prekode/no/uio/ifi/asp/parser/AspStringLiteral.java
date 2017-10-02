package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspStringLiteral extends AspAtom {

    public String value;
    AspStringLiteral(int n, String s){
        this.super(n);
        this.value = s;
    }

    static AspStringLiteral parse(Scanner s){
        Main.log.enterParser("string litteral");
        if(!s.isStringToken()){
            parserError("Expected a stringToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspStringLiteral abl = new AspStringLiteral(s.curLineNum(), s.curToken.stringLit);
        Main.log.leaveParser("string litteral");
        return abl;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" " + value.toString() + " ");
    }
}
