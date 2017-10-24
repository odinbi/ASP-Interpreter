package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspReturnStmt extends AspStmt{

    AspExpr expr;

    AspReturnStmt(int n){
        super(n);
    }

    static AspReturnStmt parse(Scanner s){
        Main.log.enterParser("return stmt");
        AspReturnStmt rtrn = new AspReturnStmt(s.curLineNum());
        skip(s, returnToken);
        rtrn.expr = AspExpr.parse(s);
        skip(s, newLineToken);
        Main.log.leaveParser("return stmt");
        return rtrn;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("return ");
        expr.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        Main.rlog.enterEval("AspReturnStmt");
        RuntimeValue temp = expr.eval(curScope);
        Main.rlog.leaveEval("AspReturnStmt");
        return expr.eval(curScope);
    }
}
