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

    /**
    * parse
    * returns: AspComparison
    * input: Scanner
    *
    * parse takes a Scanner s object, sends s to AspTerm.parse,
    * expects AspTerm.parse to traverse Scanner token list by one token.
    * If the next token is a comparison operator sends s to AspCompOpr.parse.
    * Expects AspCompOpr.parse to traverse Scanner token list by one token.
    */
    static AspComparison parse(Scanner s) {
        Main.log.enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
        while(true){
            ac.term.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            ac.cmpopr.add(AspCompOpr.parse(s));
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
        RuntimeValue temp = term.get(0).eval(curScope);
        RuntimeValue rtrn = temp;
        RuntimeValue temp2;
        String opr = null;
        Main.rlog.enterEval("AspComparison");
        for(int i = 0; i < cmpopr.size(); i++){
            opr = cmpopr.get(i).value.toString();
            System.out.println("\t\tCurrent term: " + temp.toString() + ", current comparison: " + opr);
            temp2 = temp;
            temp = term.get(i+1).eval(curScope);
            if(opr.equals("<")){
                rtrn = temp2.evalLess(temp, this);
            } else if(opr.equals(">")){
                rtrn = temp2.evalGreater(temp, this);
            } else if(opr.equals("==")){
                rtrn = temp2.evalEqual(temp, this);
            } else if(opr.equals(">=")){
                rtrn = temp2.evalGreaterEqual(temp, this);
            } else if(opr.equals("<=")){
                rtrn = temp2.evalLessEqual(temp, this);
            } else if(opr.equals("!=")){
                rtrn = temp2.evalNotEqual(temp, this);
            } else{
                RuntimeValue.runtimeError("comparison operation undefined for operator "
                                                + opr.toString() + "!", this);
            }
            if(!rtrn.getBoolValue(rtrn.toString(), this)) break;
        }
        Main.rlog.leaveEval("AspComparison");
        return rtrn;
    }
}
