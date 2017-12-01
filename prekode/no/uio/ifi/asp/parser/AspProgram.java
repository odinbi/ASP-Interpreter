package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import static no.uio.ifi.asp.runtime.RuntimeValue.runtimeError;

public class AspProgram extends AspSyntax {
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	       super(n);
    }

    /**
    * parse
    * return parameter: ap <AspProgram>
    * input parameter: s <Scanner>
    *
    * parse creates an instance of the active class,
    * for each token read by the scanner the method
    * calls AspStatement to parse the token and adds
    * the return value (AspStmt) to the list stmts.
    * parse epxects that AspStmt.parse sets the
    * curToken pointer in the scanner to a succeeding
    * token of what the method recieved when first called.
    * When last token in the scanner is reached
    * it returns ap.
    */
    public static AspProgram parse(Scanner s) {
        Main.log.enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while(s.curToken().kind != eofToken){
            ap.stmts.add(AspStmt.parse(s));
            if(s.peek().kind == eofToken) break;
            s.curToken();
        }
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
        RuntimeValue buffer = new RuntimeNoneValue();
        for(AspStmt stmt : stmts){
            try{
                buffer = stmt.eval(curScope);
            } catch(RuntimeReturnValue rrv){
                runtimeError("Return statement not inside function", this);
            }
        }
        trace("program " + buffer.showInfo());
        return buffer;
    }
}
