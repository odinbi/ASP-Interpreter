package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factor = new ArrayList<>();
    ArrayList<AspTermOpr> oprs = new ArrayList<>();

    AspTerm(int n){
        super(n);
    }

    static AspTerm parse(Scanner s) {
        Main.log.enterParser("term");
        AspTerm at = new AspTerm(s.curLineNum());

        while(true){
            at.factor.add(AspFactor.parse(s));
            /*if(s.isTermOpr(s.peekNext())){
                s.readNextToken();
            }*/
            if (!s.isTermOpr()) break;
            //s.readNextToken();
            at.oprs.add(AspTermOpr.parse(s));
        }

        Main.log.leaveParser("term");
        return at;
    }

    @Override
    void prettyPrint() {
        int nPrinted = -1;
        for (AspFactor fac: factor) {
            if (nPrinted >= 0)
                oprs.get(nPrinted).prettyPrint();
            fac.prettyPrint(); ++nPrinted;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
