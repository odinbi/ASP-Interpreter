package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;
import java.util.ArrayList;

class AspIfStmt extends AspStmt{

    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspSuite> suite = new ArrayList<>();

    AspIfStmt(int n){
        super(n);
    }

    static AspIfStmt parse(Scanner s){
        Main.log.enterParser("if stmt");
        AspIfStmt ais = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);

        while(true){
            ais.expr.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));

            if (s.curToken().kind != elifToken){
                break;
            } else{
                skip(s, elifToken);
            }
        }

        if (s.curToken().kind == elseToken){
            skip(s, elseToken);
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));
        }
        Main.log.leaveParser("if stmt");
        return ais;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("if ");
    	for(int i = 0; i < suite.size(); i++){
            if(i > 0 && i == suite.size()-1){
                Main.log.prettyWrite("else");
            } else if(i > 0 && i != suite.size()-1){
                Main.log.prettyWrite("elif ");
                expr.get(i).prettyPrint();
            } else{
                expr.get(i).prettyPrint();
            }
            Main.log.prettyWrite(": ");
            suite.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        Main.rlog.enterEval("AspIfStmt");
        int i = 0;
        for(i = 0; i < expr.size(); i++){
            if(expr.get(i).eval(curScope).getBoolValue(expr.get(i).eval(curScope).toString(), this)){
                suite.get(i).eval(curScope);
                break;
            }
        }
        Main.rlog.leaveEval("AspIfStmt");
        return null;
    }
}
