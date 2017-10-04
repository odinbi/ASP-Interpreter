package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspProgram extends AspSyntax {
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	       super(n);
    }


    public static AspProgram parse(Scanner s) {
        Main.log.enterParser("program");
        s.curToken();
        System.out.println("First token: " + s.curToken().kind.toString());
        AspProgram ap = new AspProgram(s.curLineNum());
        do {
            System.out.println("curToken: " + s.curToken().kind.toString());
            ap.stmts.add(AspStmt.parse(s));
            s.readNextToken();
        } while (s.curToken().kind != eofToken);
    	/*while (s.curToken().kind != eofToken) {
            System.out.println("While loop?");
            //s.readNextToken();
    	}
        */
    	Main.log.leaveParser("program");
    	return ap;
    }


    @Override
    public void prettyPrint() {
    	for(AspStmt stmt : stmts){
            stmt.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
