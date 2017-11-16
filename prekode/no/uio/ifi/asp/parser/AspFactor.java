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
        Main.rlog.enterEval("AspFactor");
        RuntimeValue temp = primary.get(0).eval(curScope);
        String curopr = null;
        String curprefix = null;
        if(prefix != null){
            curprefix = prefix.eval(curScope).getOpr();
            if(curprefix.equals("-")){
                temp.evalNegate(this);
            }
        }

        for(int i = 0; i < oprs.size(); i++){
            curopr = oprs.get(i).value.toString();
            Main.rlog.enterMessage("Current primary: " + temp.toString()
                + ", current operator: " + curopr + ", next primary "
                + primary.get(i+1).eval(curScope).toString());
            if(curopr.equals("*")){
                temp = temp.evalMultiply(primary.get(i+1).eval(curScope), this);
            } else if(curopr.equals("/")){
                temp = temp.evalDivide(primary.get(i+1).eval(curScope), this);
            } else if(curopr.equals("//")){
                temp = temp.evalIntDivide(primary.get(i+1).eval(curScope), this);
            } else if(curopr.equals("%")){
                temp = temp.evalModulo(primary.get(i+1).eval(curScope), this);
            } else{
                RuntimeValue.runtimeError("factor operation undefined for operator "
                                                + curopr + "!", this);
            }
        }
        Main.rlog.leaveEval("AspFactor");
        return temp;
    }
}
