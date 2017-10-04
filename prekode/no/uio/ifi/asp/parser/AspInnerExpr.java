package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspInnerExpr extends AspAtom {

    AspExpr expr;

    AspInnerExpr(int n){
        super(n);
    }

    static AspInnerExpr parse(Scanner s){
        Main.log.enterParser("inner expr");

        skip(s, leftParToken);
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
        aie.expr = AspExpr.parse(s);
        //s.readNextToken();
        skip(s, rightParToken);

        Main.log.leaveParser("inner expr");
        return aie;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("(");
        expr.prettyPrint();
        Main.log.prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
