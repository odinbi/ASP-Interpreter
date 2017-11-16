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
        Main.rlog.enterEval("AspAssignment");
        String nm = name.value;
        Main.rlog.enterMessage("Name to assing value: " + nm);
        RuntimeValue val = expr.eval(curScope);
        if(subscr.size() > 0){
            RuntimeListValue templist = new RuntimeListValue();
            for(int i = 0; i < subscr.size()-1; i++){
                templist.evalSubscription(subscr.get(i).eval(curScope), this);
            }
            RuntimeValue last = subscr.get(subscr.size()-1).eval(curScope);
            templist.evalAssignElem(last, val, this);
            curScope.assign(nm, templist);
        } else{
            curScope.assign(nm, val);
        }
        if(val != null){
            Main.rlog.enterMessage("From scope: " + nm + " = " + curScope.find(nm, this));
            Main.rlog.enterMessage(nm + " = " + val.getTypeName());
            Main.rlog.enterMessage(nm + " = " + val.toString());
        }
        Main.rlog.leaveEval("AspAssignment");
        return val;
    }
}
