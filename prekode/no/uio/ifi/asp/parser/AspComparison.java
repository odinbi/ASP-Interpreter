package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspComparison extends AspSyntax {
    ArrayList<AspTerm> term = new ArrayList<>();
    ArrayList<AspCompOpr> cmpopr = new ArrayList<>();

    AspComparison(int n){
        super(n);
    }

    static AspComparison parse(Scanner s) {
        Main.log.enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
        while(true){
            ac.term.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            //s.readNextToken();
            ac.cmpopr.add(AspCompOpr.parse(s));
            s.readNextToken();
        }

        Main.log.leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        int nPrinted = -1;
        for (AspTerm trm: term) {
            if (nPrinted >= 0)
                cmpopr.get(nPrinted).prettyPrint();
            trm.prettyPrint(); ++nPrinted;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
