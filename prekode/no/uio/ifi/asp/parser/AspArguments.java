package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;
import java.util.ArrayList;

class AspArguments extends AspPrimarySuffix{
    public ArrayList<AspExpr> aspExpr= new ArrayList<>();

    AspArguments(int n){
        super(n);
    }

    static AspArguments parse(Scanner s){
        Main.log.enterParser("arguments");
        AspArguments aa = new AspArguments(s.curLineNum());
        if(s.curToken().kind == leftParToken) skip(s, leftParToken);
        while(true){
            aa.aspExpr.add(AspExpr.parse(s));
            if(s.peekNext().kind != commaToken) break;
            s.readNextToken();
            skip(s, commaToken);
        }
        if(s.peekNext().kind == rightParToken){
            s.readNextToken();
            skip(s, rightParToken);
        }
        else parserError("expected rightParToken but found: " +
                        s.curToken().kind + "! ", s.curLineNum());
        Main.log.leaveParser("arguments");
        return aa;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite("(");
        int nComma = 0;
        for(AspExpr expr : aspExpr){
            if(nComma > 0) Main.log.prettyWrite(", ");
            expr.prettyPrint();
            nComma++;
        }
        Main.log.prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
