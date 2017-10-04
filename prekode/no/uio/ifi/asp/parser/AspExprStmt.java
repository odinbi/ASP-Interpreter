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

    static AspExprStmt parse(Scanner s){
        Main.log.enterParser("expr stmt");
        System.out.println("curToken: " + s.curToken().kind.toString());
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
        //-- Must be changed in part 4:
        return null;
    }
}
