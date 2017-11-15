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
            ArrayList<RuntimeValue> tmp = new ArrayList<>();
            RuntimeValue retVal = null;
            for(AspPrimarySuffix suf : suffixes){
                val = suf.eval(curScope);
                tmp.add(val);
                if(suf instanceof AspSubscription){
                    retVal = atm.evalSubscription(suf.eval(curScope), this);
                } else if(val instanceof RuntimeStringValue){
                    retVal = atm.evalFuncCall(tmp, curScope, this);
                }
                else{
                    RuntimeArgumentsValue temp = (RuntimeArgumentsValue)val;
                    retVal = atm.evalFuncCall(temp.getRawList(), curScope, this);
                }
                tmp.clear();
            }
            Main.rlog.enterMessage(atm.toString() + ", value: " + retVal.toString());
            //curScope.assign(atm.toString(), retVal);
        }
        Main.rlog.enterMessage(atm.toString() + ", value: " + curScope.find(atm.toString(), this));
        Main.rlog.leaveEval("AspPrimary");
        return atm;
    }
}
