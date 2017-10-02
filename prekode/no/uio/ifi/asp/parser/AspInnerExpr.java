package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspInnerExpr extends AspAtom {

    AspExpr expr;

    static AspInnerExpr parse(Scanner s){
        Main.log.enterParser("inner expr");

        s.skip(s, leftParToken);
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
        aie.expr = AspExpr.parse(s);
        s.skip(s, rightParToken);

        Main.log.leaveParser("inner expr");
        return aie;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" ( ");
        expr.prettyPrint();
        Main.log.prettyWrite(" ) ");
    }
}
