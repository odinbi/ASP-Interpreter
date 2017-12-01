package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspWhileStmt extends AspStmt{
    AspExpr expr;
    AspSuite suite;

    AspWhileStmt(int n){
        super(n);
    }

    static AspWhileStmt parse(Scanner s){
        Main.log.enterParser("while stmt");
        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());

        skip(s, whileToken);
        aws.expr = AspExpr.parse(s);
        skip(s, colonToken);
        aws.suite = AspSuite.parse(s);
        Main.log.leaveParser("while stmt");
        return aws;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite("while ");
        expr.prettyPrint();
        Main.log.prettyWrite(":");
        suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue boolval = expr.eval(curScope);
        while(boolval.getBoolValue(boolval.toString(), this)){
            suite.eval(curScope);
            boolval = expr.eval(curScope);
        }
        return null;
    }
}
