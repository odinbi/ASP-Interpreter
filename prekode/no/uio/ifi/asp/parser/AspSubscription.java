package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspSubscription extends AspPrimarySuffix{
    AspExpr expr;

    AspSubscription(int n){
        super(n);
    }

    static AspSubscription parse(Scanner s){
        Main.log.enterParser("subscription");

        AspSubscription subscr = new AspSubscription(s.curLineNum());

        skip(s, leftBracketToken);
        subscr.expr = AspExpr.parse(s);
        skip(s, rightBracketToken);

        Main.log.leaveParser("subscription");
        return subscr;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("[");
        expr.prettyPrint();
        Main.log.prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue temp = expr.eval(curScope);
        return temp;
    }
}
