package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspNoneLiteral extends AspAtom {

    public String value;

    AspNoneLiteral(int n){
        super(n);
        this.value = "NONE";
    }

    static AspNoneLiteral parse(Scanner s){
        Main.log.enterParser("none litteral");
        if(s.curToken().kind != noneToken){
            parserError("Expected a noneToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        skip(s, noneToken);
        Main.log.leaveParser("none litteral");
        return anl;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("" + value.toString());
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
