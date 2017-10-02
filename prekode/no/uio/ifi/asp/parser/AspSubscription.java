package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspSubscription extends AspPrimarySuffix{

    ArrayList<AspExpr> expr = new ArrayList<>();

    static AspSubscription parse(Scanner s){
        Main.log.enterParser("subscription");

        AspSubscription subscr = new AspSubscription(s.curLineNum());

        if(s.curToken().kind == leftBracketToken) skip(s, leftBracketToken);
        subscr.expr.add(AspExpr.parse(s));
        if(s.curToken().kind == rightBracketToken) skip(s, rightBracketToken);
        else parserError ("expected a rightBracketToken but found: " +
                            s.curToken().kind + "!" + s.curLineNum);

        Main.log.leaveParser("subscription");
        return subscr;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" [ ");
        expr.prettyPrint();
        Main.log.prettyWrite(" ] ");
    }
}
