package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;
import java.util.ArrayList;

class AspAssignment extends AspStmt{
    AspName name;
    ArrayList<AspSubscription> subscr = new ArrayList<>();
    AspExpr expr;

    AspAssignment(int n){
        super(n);
    }

    /**
    * parse
    * returns: AspAssignment
    * input: Scanner
    *
    * parse takes a Scanner s object, sends s to AspSubscription.parse,
    * expects AspSubscription.parse to traverse Scanner token list by one token.
    */
    static AspAssignment parse(Scanner s){
        Main.log.enterParser("assignment");
        AspAssignment aa = new AspAssignment(s.curLineNum());

        aa.name = AspName.parse(s);
        while(true){
            if(s.curToken().kind == equalToken){
                skip(s, equalToken);
                break;
            }
            aa.subscr.add(AspSubscription.parse(s));
        }
        aa.expr = AspExpr.parse(s);

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
        Main.log.prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        String nm = name.value;
        if(subscr.size() > 0){
            for(AspSubscription sub : subscr){
                nm = nm + "[" + sub.eval(curScope).toString() + "]";
            }
        }
        curScope.assign(nm, expr.eval(curScope));
        return null;
    }
}
