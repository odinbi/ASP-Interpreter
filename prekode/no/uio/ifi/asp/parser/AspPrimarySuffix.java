package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

abstract class AspPrimarySuffix extends AspSyntax {

    AspPrimarySuffix(int n){
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s){
        Main.log.enterParser("primary suffix");
        AspPrimarySuffix suffix = null;
        switch(s.curToken().kind){
            case leftBracketToken:
                suffix = AspSubscription.parse(s); break;
            case leftParToken:
                suffix = AspArguments.parse(s); break;
            default:
                parserError("Expected an [ or ( but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("primary suffix");
        return suffix;
    }
}
