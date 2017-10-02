package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

abstract class AspFactorPrefix extends AspSyntax {
    TokenKind value;
    AspFactorPrefix(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspFactorPrefix parse(Scanner s) {
        Main.log.enterParser("factor prefix");

        switch (s.curToken().kind) {
            case plusToken:
            case minusToken:
                AspFactorPrefix a = new AspFactorPrefix(s.curLineNum(), s.curToken.kind());
                break;
            default:
                parserError("Expected an factor prefix but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("factor prefix");
        return a;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" " + value.toString());
    }
}
