package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspNotTest extends AspSyntax {
    ArrayList<AspComparison> comparison = new ArrayList<>();
    static AspNotTest parse(Scanner s) {
        Main.log.enterParser("not test");
        AspNotTest ant = new AspNotTest(s.curLineNum());
        ant.comparison.add(AspComparison.parse(s));
        if (s.curToken().kind == notToken){
            skip(s, notToken);
            ant.comparison.add(AspComparison.parse(s));
        }

        Main.log.leaveParser("not test");
        return ant;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspNotTest ant: notTests) {
            if (nPrinted > 0)
                Main.log.prettyWrite(" not ");
            ant.prettyPrint(); ++nPrinted;
        }
    }
}
