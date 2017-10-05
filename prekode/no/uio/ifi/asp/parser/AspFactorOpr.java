package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspFactorOpr extends AspSyntax {
    TokenKind value;
    AspFactorOpr(int n, TokenKind tk){
        super(n);
        this.value = tk;
    }

    static AspFactorOpr parse(Scanner s) {
        Main.log.enterParser("factor opr");

        AspFactorOpr a = null;
        switch (s.curToken().kind) {
            case astToken:
            case slashToken:
            case doubleSlashToken:
            case percentToken:
                a = new AspFactorOpr(s.curLineNum(), s.curToken().kind);
                skip(s, s.curToken().kind);
                break;
            default:
                parserError("Expected an factor opr but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("factor opr");
        return a;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" " + value.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
