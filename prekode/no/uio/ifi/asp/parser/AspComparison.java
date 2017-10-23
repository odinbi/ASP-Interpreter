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
        String opr = null;
        for(int i = 0; i < cmpopr.size(); i++){
            opr = cmpopr.get(i).toString();
            if(opr.equals("<")){
                temp = temp.evalLess(term.get(i+1).eval(curScope), this);
            } else if(opr.equals(">")){
                temp = temp.evalGreater(term.get(i+1).eval(curScope), this);
            } else if(opr.equals("==")){
                temp = temp.evalEqual(term.get(i+1).eval(curScope), this);
            } else if(opr.equals(">=")){
                temp = temp.evalGreaterEqual(term.get(i+1).eval(curScope), this);
            } else if(opr.equals("<=")){
                temp = temp.evalLessEqual(term.get(i+1).eval(curScope), this);
            } else if(opr.equals("!=")){
                temp = temp.evalNotEqual(term.get(i+1).eval(curScope), this);
            } else{
                RuntimeValue.runtimeError("comparison operation undefined for operator"
                                                + opr.toString() + "!", this);
            }
        }
        return temp;
    }
}
