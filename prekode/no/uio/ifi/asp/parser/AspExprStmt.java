package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspExprStmt extends AspStmt{

    AspExpr expr;

    static AspExprStmt parse(Scanner s){
        Main.log.enterParser("expr stmt");
        AspExprStmt aes = new AspExprStmt(s.curLineNum());
        aes.expr = AspExpr.parse(s);
        skip(s, newLineToken);
        Main.log.leaveParser("expr stmt");
        return aes;
    }

    @Override
    public void prettyPrint() {
        expr.prettyPrint();
        Main.log.prettyWrite("\n");
    }
}
