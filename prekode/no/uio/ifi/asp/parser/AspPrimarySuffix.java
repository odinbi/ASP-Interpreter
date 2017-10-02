package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

abstract class AspPrimarySuffix extends AspSyntax {

    AspSyntax as;

    static AspPrimarySuffix parse(Scanner s){
        Main.log.enterParser("primary suffix");
        AspPrimarySuffix suffix = new AspPrimarySuffix(s.curLineNum());
        switch(s.curToken().kind){
            case leftBracketToken:
                suffix.as = AspArguments.parse(s); break;
            case leftParToken:
                suffix.as = AspSubscription.parse(s); break;
            default:
                parserError("Expected an [ or ( but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("primary suffix");
        return suffix;
    }

    @Override
    public void prettyPrint() {
        as.prettyPrint();
    }
}
