package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;
import java.util.ArrayList;

class AspDictDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspStringLiteral> str = new ArrayList<>();

    AspDictDisplay(int n){
        super(n);
    }

    /**
    * parse
    * returns: AspDictDisplay
    * input: Scanner
    *
    * parse takes a Scanner s object, sends s to AspStringLiteral.parse and
    * AspExpr.parse, expects that each parse will set the pointer of the
    * Scanner token list to the next token.
    *
    */
    static AspDictDisplay parse(Scanner s){
        Main.log.enterParser("dict display");
        AspDictDisplay a = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);
        if(s.curToken().kind != rightBraceToken){
            while (true) {
                if(s.curToken().kind == stringToken){
                    a.str.add(AspStringLiteral.parse(s));
                    skip(s, colonToken);
                    a.expr.add(AspExpr.parse(s));
                    if (s.curToken().kind != commaToken) break;
                    skip(s, commaToken);
                }
                else break;
            }
        }
        skip(s, rightBraceToken);
        Main.log.leaveParser("dict display");
        return a;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("{");
        for(int i = 0; i < expr.size(); i++){
            if(i > 0) Main.log.prettyWrite(", ");
            str.get(i).prettyPrint();
            Main.log.prettyWrite(": ");
            expr.get(i).prettyPrint();
        }
        Main.log.prettyWrite("}");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeDictionaryValue dict = new RuntimeDictionaryValue(str.get(0).toString(), expr.get(0).eval(curScope));
        System.out.println("\t@AspDictDisplay.eval()");
        for(int i = 1; i < str.size(); i++){
            dict.addEntry(str.get(i).value, expr.get(i).eval(curScope));
        }
        System.out.println("\t/@AspDictDisplay.eval()");
        return dict;
    }
}
