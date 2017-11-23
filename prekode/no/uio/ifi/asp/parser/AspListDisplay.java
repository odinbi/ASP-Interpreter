package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspListDisplay(int n){
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        Main.log.enterParser("list display");
        AspListDisplay ald = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);
        if(s.curToken().kind != rightBracketToken){
            while (true) {
                ald.expr.add(AspExpr.parse(s));
                if (s.curToken().kind != commaToken)
                    break;
                skip(s, commaToken);
            }
        }
        skip(s, rightBracketToken);
        Main.log.leaveParser("list display");
        return ald;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("[");
        int nComma = 0;
        for(AspExpr exp : expr){
            if(nComma > 0) Main.log.prettyWrite(", ");
            exp.prettyPrint();
            nComma++;
        }
        Main.log.prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        Main.rlog.enterEval("AspListDisplay");
        RuntimeListValue lst = new RuntimeListValue();
        if(expr.size() > 0){
            for(int i = 0; i < expr.size(); i++){
                lst.evalAppend(expr.get(i).eval(curScope));
            }
        }
        Main.rlog.enterMessage("Check list: " + lst);
        Main.rlog.leaveEval("AspListDisplay");
        return lst;
    }
}
