package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspFactorPrefix extends AspSyntax {
    TokenKind value;
    AspFactorPrefix(int n, TokenKind tk){
        super(n);
        this.value = tk;
    }

    static AspFactorPrefix parse(Scanner s) {
        Main.log.enterParser("factor prefix");
        AspFactorPrefix a = null;

        switch (s.curToken().kind) {
            case plusToken:
            case minusToken:
                a = new AspFactorPrefix(s.curLineNum(), s.curToken().kind);
                skip(s, s.curToken().kind);
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
        Main.log.prettyWrite(value.toString());
    }

    @Override
    public RuntimeOperatorValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        Main.rlog.enterEval("AspFactorPrefix");
        RuntimeOperatorValue opr = null;
        switch (value) {
            case plusToken:
                opr = new RuntimeOperatorValue("+");
                break;
            case minusToken:
                opr = new RuntimeOperatorValue("-");
                break;
            default:
                RuntimeValue.runtimeError("Illegal factor prefix "
                                    + value.toString() + "!", this);
        }
        Main.rlog.leaveEval("AspFactorPrefix");
        return opr;
    }
}
