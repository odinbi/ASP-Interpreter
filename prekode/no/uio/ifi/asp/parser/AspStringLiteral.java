package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspStringLiteral extends AspAtom {
    public String value;

    AspStringLiteral(int n, String s){
        super(n);
        this.value = s;
    }

    static AspStringLiteral parse(Scanner s){
        Main.log.enterParser("string litteral");
        if(s.curToken().kind != stringToken){
            parserError("Expected a stringToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspStringLiteral abl = new AspStringLiteral(s.curLineNum(),
                                                    s.curToken().stringLit);
        skip(s, stringToken);
        Main.log.leaveParser("string litteral");
        return abl;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("\"" + value.toString() + "\"");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
