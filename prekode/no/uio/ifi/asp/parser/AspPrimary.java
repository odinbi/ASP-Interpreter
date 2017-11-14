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
        //temporary solution
        Main.rlog.enterEval("AspPrimary");
        RuntimeValue atm = atom.eval(curScope);
        if(suffixes.size() > 0){
            if(atm instanceof RuntimeFunc){
                try{
                    atm = atm.evalFuncCall(suffixes.get(0).eval(curScope));
                } catch(RuntimeReturnValue rtval){
                    atm = rtval;
                }
            } else if(!(atm instanceof RuntimeListValue || atm instanceof RuntimeDictionaryValue || atm instanceof RuntimeStringValue)){
                atm.runtimeError("Could not recognize type: " + atm.toString() + "!", this);
            } else{
                for(AspPrimarySuffix suf : suffixes){
                    atm = atm.evalSubscription(suf.eval(curScope));
                }
            }
        }
        Main.rlog.leaveEval("AspPrimary");
        return atm;
    }
}
