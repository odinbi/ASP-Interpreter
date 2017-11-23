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
        Main.rlog.enterEval("AspComparison");
        RuntimeValue temp = term.get(0).eval(curScope);
        RuntimeValue temp2;
        String opr = null;
        for(int i = 0; i < cmpopr.size(); i++){
            opr = cmpopr.get(i).value.toString();
            temp2 = term.get(i+1).eval(curScope);
            Main.rlog.enterMessage("Current term: " + temp.toString() + ", current comparison: " + opr + ", next term: " + temp2.toString());
            if(opr.equals("<")){
                temp = temp.evalLess(temp2, this);
            } else if(opr.equals(">")){
                temp = temp.evalGreater(temp2, this);
            } else if(opr.equals("==")){
                temp = temp.evalEqual(temp2, this);
            } else if(opr.equals(">=")){
                temp = temp.evalGreaterEqual(temp2, this);
            } else if(opr.equals("<=")){
                temp = temp.evalLessEqual(temp2, this);
            } else if(opr.equals("!=")){
                temp = temp.evalNotEqual(temp2, this);
            } else{
                RuntimeValue.runtimeError("comparison operation undefined for operator "
                                                + opr.toString() + "!", this);
            }
            if(!temp.getBoolValue(temp.toString(), this)) break;
        }
        Main.rlog.leaveEval("AspComparison");
        return temp;
    }
}
