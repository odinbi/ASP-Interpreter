package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspFactor extends AspSyntax {

    AspFactorPrefix prefix;
    ArrayList<AspPrimary> primary = new ArrayList<>();
    ArrayList<AspFactorOpr> oprs = new ArrayList<>();

    AspFactor(int n){
        super(n);
    }

    static AspFactor parse(Scanner s) {
        Main.log.enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());

        if (s.isFactorPrefix()){
            af.prefix = AspFactorPrefix.parse(s);
            s.readNextToken();
        }

        while(true){
            af.primary.add(AspPrimary.parse(s));
            if (!s.isFactorOpr(s.peekNext())) break;
            s.readNextToken();
            af.oprs.add(AspFactorOpr.parse(s));
            s.readNextToken();
        }

        Main.log.leaveParser("factor");
        return af;
    }

    @Override
    void prettyPrint() {
        if(prefix != null)
            prefix.prettyPrint();

        int nPrinted = 0;
        for (AspPrimary prim: primary) {
            if (nPrinted > 0)
                oprs.get(nPrinted).prettyPrint();
            prim.prettyPrint(); ++nPrinted;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
