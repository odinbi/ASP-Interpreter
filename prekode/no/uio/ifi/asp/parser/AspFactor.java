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

        int nPrinted = -1;
        for (AspPrimary prim: primary) {
            if (nPrinted >= 0)
                oprs.get(nPrinted).prettyPrint();
            prim.prettyPrint(); ++nPrinted;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue temp = primary.get(0).eval(curScope);
        RuntimeValue temp2;

        if(prefix != null){
            String curprefix = prefix.eval(curScope).getOpr();
            if(curprefix.equals("-")){
                temp.evalNegate(this);
            }
        }

        String curopr = null;
        for(int i = 0; i < oprs.size(); i++){
            curopr = oprs.get(i).value.toString();
            temp2 = primary.get(i+1).eval(curScope);
            if(curopr.equals("*")){
                temp = temp.evalMultiply(temp2, this);
            } else if(curopr.equals("/")){
                temp = temp.evalDivide(temp2, this);
            } else if(curopr.equals("//")){
                temp = temp.evalIntDivide(temp2, this);
            } else if(curopr.equals("%")){
                temp = temp.evalModulo(temp2, this);
            } else{
                RuntimeValue.runtimeError("factor operation undefined for operator "
                                                + curopr + "!", this);
            }
        }
        trace("Factor " + temp.showInfo());
        return temp;
    }
}
