package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;
import java.util.ArrayList;

class AspAssignment extends AspStmt{
    AspName name;
    ArrayList<AspSubscription> subscr = new ArrayList<>();
    AspExpr expr;

    AspAssignment(int n){
        super(n);
    }

    static AspAssignment parse(Scanner s){
        Main.log.enterParser("assignment");
        AspAssignment aa = new AspAssignment(s.curLineNum());

        aa.name = AspName.parse(s);

        while(true){
            s.readNextToken();
            if(s.curToken().kind == equalToken){
                skip(s, equalToken);
                break;
            }
            aa.subscr.add(AspSubscription.parse(s));
        }
        aa.expr = AspExpr.parse(s);
        System.out.println("<assignment> : current token : " + s.curToken().kind + ", next token : " + s.peekNext().kind);
        if(s.peekNext().kind != newLineToken)
            parserError("expected newLineToken but found: " +
                        s.curToken().kind + "!", s.curLineNum());
        s.readNextToken();
        skip(s, newLineToken);
        Main.log.leaveParser("assignment");
        return aa;
    }

    @Override
    public void prettyPrint() {
        name.prettyPrint();
        for(AspSubscription sub : subscr){
            sub.prettyPrint();
        }
        Main.log.prettyWrite(" = ");
        expr.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
