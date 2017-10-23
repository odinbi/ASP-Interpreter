package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspExprStmt extends AspStmt{

    AspExpr expr;

    AspExprStmt(int n){
        super(n);
    }

    /**
    * parse
    * returns: AspExprStmt
    * input: Scanner
    *
    * parse takes a Scanner s object, sends s to AspExpr.parse
    * expects that the parse will set the pointer of the Scanner token list
    * to the next token.
    *
    */
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
        Main.log.prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return expr.eval(curScope);
    }
}
