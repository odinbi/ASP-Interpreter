package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
        super(n);
    }


    public static AspExpr parse(Scanner s) {
        System.out.println("curToken: " + s.curToken().kind.toString());
        Main.log.enterParser("expr");
        AspExpr ae = new AspExpr(s.curLineNum());
        while(true){
            ae.andTests.add(AspAndTest.parse(s));
            if(s.peekNext().kind != orToken) break;
            s.readNextToken();
            skip(s, orToken);
        }
        Main.log.leaveParser("expr");
        return ae;
    }


    @Override
    public void prettyPrint() {
        int nOr = 0;
        for(AspAndTest andTest : andTests){
            if(nOr > 0) Main.log.prettyWrite(" or ");
            andTest.prettyPrint();
            nOr++;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
