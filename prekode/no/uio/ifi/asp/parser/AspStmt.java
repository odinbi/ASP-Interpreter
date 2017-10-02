package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

abstract class AspStmt extends AspSyntax {
    static AspStmt parse(Scanner s){
        Main.log.enterParser("asp stmt");
        AspStmt as = null;

        switch(s.curToken().kind){
            case nameToken:
                as = AspAssignment.parse(s); break;
            case ifToken:
                as = AspIfStmt.parse(s); break;
            case whileToken:
                as = AspWhileStmt.parse(s); break;
            case returnToken:
                as = AspReturnStmt.parse(s); break;
            case passToken:
                as = AspPassStmt.parse(s); break;
            case defToken:
                as = AspFuncDef.parse(s); break;
            default:
                as = AspExpr.parse(s);
        }
        Main.log.leaveParser("asp stmt");
        return as;
    }

    @Override
    public void prettyPrint() {
    	as.prettyPrint();
    }
}
