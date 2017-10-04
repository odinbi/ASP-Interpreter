package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspFuncDef extends AspStmt{
    AspName funcName;
    ArrayList<AspName> name = new ArrayList<>();
    AspSuite suite;

    AspFuncDef(int n){
        super(n);
    }

    static AspFuncDef parse(Scanner s){
        Main.log.enterParser("func def");
        AspFuncDef func = new AspFuncDef(s.curLineNum());
        skip(s, defToken);
        func.funcName = AspName.parse(s);
        skip(s, leftParToken);
        if(s.curToken().kind != rightParToken){
            while(true){
                func.name.add(AspName.parse(s));
                if(s.curToken().kind != commaToken) break;
                skip(s, commaToken);
            }
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        func.suite = AspSuite.parse(s);
        Main.log.leaveParser("func def");
        return func;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("def ");
        funcName.prettyPrint();
        Main.log.prettyWrite("(");
        int nCommas = 0;
        for(AspName nm : name){
            if(nCommas > 0) Main.log.prettyWrite(", ");
            nm.prettyPrint();
            nCommas++;
        }
        Main.log.prettyWrite(")");
        Main.log.prettyWrite(": ");
        suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
