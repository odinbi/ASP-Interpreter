package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspName extends AspAtom{

    public String value;

    AspName(int n, String s){
        super(n);
        this.value = s;
    }

    static AspName parse(Scanner s){
        Main.log.enterParser("name");
        if(s.curToken().kind != nameToken){
            parserError("Expected a nameToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspName an = new AspName(s.curLineNum(), s.curToken().name);
        Main.log.leaveParser("name");
        return an;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(value.toString());
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
