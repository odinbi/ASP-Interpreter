package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspPrimary extends AspSyntax {
    AspAtom atom;
    ArrayList<AspPrimarySuffix> suffixes = new ArrayList<>();

    AspPrimary(int n){
        super(n);
    }

    static AspPrimary parse(Scanner s) {
        Main.log.enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s);
        while(true){
            if (s.curToken().kind != leftParToken &&
                s.curToken().kind != leftBracketToken)
                break;
            ap.suffixes.add(AspPrimarySuffix.parse(s));
        }

        Main.log.leaveParser("primary");
        return ap;
    }

    @Override
    void prettyPrint() {
        atom.prettyPrint();
        for (AspPrimarySuffix suffix: suffixes) {
            suffix.prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        Main.rlog.enterEval("AspPrimary");
        RuntimeValue atm = atom.eval(curScope);
        Main.rlog.enterMessage(atm.toString());
        if(suffixes.size() > 0){
            RuntimeValue val;
            RuntimeValue retVal = null;
            for(AspPrimarySuffix suf : suffixes){
                val = suf.eval(curScope);
                if(suf instanceof AspSubscription){
                    Main.rlog.enterMessage("Return was a subscription");
                    retVal = atm.evalSubscription(suf.eval(curScope), this);
                } else{
                    Main.rlog.enterMessage("Return was an argument");
                    RuntimeArgumentsValue temp = (RuntimeArgumentsValue)val;
                    retVal = atm.evalFuncCall(temp.getRawList(), curScope, this);
                }
            }
            if(atm == null){
                Main.rlog.enterMessage("STRANGE BEHAVIOUR: atm is now set to null");
            }
            if(retVal == null){
                Main.rlog.enterMessage("STRANGE BEHAVIOUR: retVal is now set to null");
            }
            if(atm != null && retVal != null)
                Main.rlog.enterMessage(atm.toString() + ", value: " + retVal.toString());
            return retVal;
        }
        Main.rlog.leaveEval("AspPrimary");
        return atm;
    }
}
