package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import static no.uio.ifi.asp.runtime.RuntimeValue.runtimeError;
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
        boolean noMoreCalls = false;
        RuntimeValue atm = atom.eval(curScope);

        for(AspPrimarySuffix suf : suffixes){
            if(noMoreCalls){
                runtimeError("Illegal function call", this);
            } else if(suf instanceof AspSubscription){
                RuntimeValue val = suf.eval(curScope);
                atm = atm.evalSubscription(val, this);
            } else{
                RuntimeArgumentsValue val = (RuntimeArgumentsValue)suf.eval(curScope);
                atm = atm.evalFuncCall(val.getRawList(), curScope, this);
                if(atm instanceof RuntimeNoneValue){
                    noMoreCalls = true;
                }
            }
        }
        return atm;
    }
}
