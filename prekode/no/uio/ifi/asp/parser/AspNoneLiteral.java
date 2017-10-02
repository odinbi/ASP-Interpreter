package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;


class AspNoneLiteral extends AspAtom {

    public Object value;

    AspNoneLiteral(int n){
        this.super(n);
        this.value = NONE;
    }

    static AspNoneLiteral parse(Scanner s){
        Main.log.enterParser("none litteral");
        if(!s.isNoneToken()){
            parserError("Expected a noneToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        Main.log.leaveParser("none litteral");
        return anl;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" " + value.toString() + " ");
    }
}
