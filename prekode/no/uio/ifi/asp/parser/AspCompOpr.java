package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspCompOpr extends AspSyntax {
    TokenKind value;
    AspCompOpr(int n, TokenKind tk){
        super(n);
        this.value = tk;
    }

    static AspCompOpr parse(Scanner s) {
        Main.log.enterParser("comp opr");
        AspCompOpr a = null;
        switch (s.curToken().kind) {
            case lessToken:
            case greaterToken:
            case doubleEqualToken:
            case greaterEqualToken:
            case lessEqualToken:
            case notEqualToken:
                a = new AspCompOpr(s.curLineNum(), s.curToken().kind);
                break;
            default:
                parserError("Expected an comp opr but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("comp opr");
        return a;
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
