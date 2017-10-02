package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspReturnStmt extends AspStmt{

    AspExpr expr;

    static AspReturnStmt parse(Scanner s){
        Main.log.enterParser("return stmt");
        AspReturnStmt rtrn = new AspReturnStmt(s.curLineNum());
        skip(s, returnToken);
        rten.expr = AspExpr.parse(s);
        skip(s, newLineToken);
        Main.log.leaveParser("return stmt");
        return rtrn;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("return ");
        expr.prettyPrint();
        Main.log.prettyWrite("\n");
    }
}
