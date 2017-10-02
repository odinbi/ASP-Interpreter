package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;
import java.util.ArrayList;

class AspAssignment extends AspStmt{

    AspName name;
    ArrayList<AspSubscription> subscr = new ArrayList<>();
    AspExpr expr;

    static AspAssignment parse(Scanner s){
        Main.log.enterParser("assignment");
        AspAssignment aa = new AspAssignment(s.curLineNum());
        aa.name = AspName.parse(s);
        while(true){
            if(s.curToken().kind == equalToken){
                skip(s, equalToken); break;
            }
            aa.subscr.add(ArraySubscription.parse(s));
        }
        aa.expr = AspExpr.parse(s);
        if(s.curToken().kind != newLineToken)
            parserError("expected newLineToken but found: " +
                        s.curToken().kind + "!" + s.curLineNum);
        skip(s, newLineToken);
        Main.log.leaveParser("assignment");
        return aa;
    }

    @Override
    public void prettyPrint() {
        name.prettyPrint();
        for(AspSubscription sub : subscr){
            sub.prettyPrint();
        }
        Main.log.prettyWrite(" = ");
        expr.prettyPrint();
        Main.log.prettyWrite("\n");
    }
}
