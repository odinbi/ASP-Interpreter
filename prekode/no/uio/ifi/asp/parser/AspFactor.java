package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspFactor extends AspSyntax {

    AspFactorPrefix prefix;
    ArrayList<AspPrimary> primary = new ArrayList<>();
    ArrayList<AspFactorOpr> oprs = new ArrayList<>();

    static AspFactor parse(Scanner s) {
        Main.log.enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());

        if (s.isFactorPrefix()){
            af.prefix = AspFactorPrefix.parse(s); //give propper token later!!!
        }

        while(true){
            af.primary.add(AspPrimary.parse(s));
            if (!s.isFactorOpr()) break;
            af.oprs.add(AspFactorOpr.parse(s));
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
                oprs[nPrinted].prettyPrint();
            prim.prettyPrint(); ++nPrinted;
        }
    }
}
