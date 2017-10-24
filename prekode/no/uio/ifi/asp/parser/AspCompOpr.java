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

    /**
    * parse
    * returns: AspCompOpr
    * input: Scanner
    *
    * parse takes a Scanner s object and returns a new instance of the class
    * with a pointer to the current token.
    * Sets the Scanner token list pointer to next token.
    *
    */
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
                skip(s, s.curToken().kind);
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
        Main.log.prettyWrite(" ");
        Main.log.prettyWrite(value.toString());
        Main.log.prettyWrite(" ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        Main.rlog.enterEval("AspCompOpr");
        RuntimeOperatorValue compr = null;
        switch (value) {
            case lessToken:
                compr = new RuntimeOperatorValue("<");
                break;
            case greaterToken:
                compr = new RuntimeOperatorValue(">");
                break;
            case doubleEqualToken:
                compr = new RuntimeOperatorValue("==");
                break;
            case greaterEqualToken:
                compr = new RuntimeOperatorValue(">=");
                break;
            case lessEqualToken:
                compr = new RuntimeOperatorValue("<=");
                break;
            case notEqualToken:
                compr = new RuntimeOperatorValue("!=");
                break;
            default:
                RuntimeValue.runtimeError("Illegal comparison operator "
                                    + value.toString() + "!", this);
        }
        Main.rlog.leaveEval("AspSubscription");
        return compr;
    }
}
