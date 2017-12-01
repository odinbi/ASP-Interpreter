package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;
import java.util.ArrayList;

class AspArguments extends AspPrimarySuffix{
    public ArrayList<AspExpr> expr= new ArrayList<>();

    AspArguments(int n){
        super(n);
    }

    /**
    * parse
    * returns: AspArguments
    * input: Scanner
    *
    * parse takes a Scanner s object, sends s to AspExpr.parse,
    * expects AspExpr.parse to traverse Scanner token list by one token.
    */
    static AspArguments parse(Scanner s){
        Main.log.enterParser("arguments");
        AspArguments aa = new AspArguments(s.curLineNum());

        skip(s, leftParToken);
        if(s.curToken().kind != rightParToken){
            while (true) {
                aa.expr.add(AspExpr.parse(s));
                if (s.curToken().kind != commaToken){
                    break;
                }
                skip(s, commaToken);
            }
        }
        skip(s, rightParToken);
        Main.log.leaveParser("arguments");
        return aa;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite("(");
        int nComma = 0;
        for(AspExpr exp : expr){
            if(nComma > 0) Main.log.prettyWrite(", ");
            exp.prettyPrint();
            nComma++;
        }
        Main.log.prettyWrite(")");
    }

    @Override
    public RuntimeArgumentsValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeArgumentsValue args = new RuntimeArgumentsValue();
        for(AspExpr arg : expr){
            args.add(arg.eval(curScope));
        }
        return args;
    }
}
