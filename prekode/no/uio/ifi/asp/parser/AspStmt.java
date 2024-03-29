package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

abstract class AspStmt extends AspSyntax {
    static AspStmt as;
    AspStmt(int n){
        super(n);
    }

    /**
    * parse
    * returns: AspStmt
    * input: Scanner
    *
    * ALL PARSE METHODS:
    * Should create an instance of the class.
    * Expects that the call of a parse, will result in a s.readNextToken() call
    * such that s.curToken() will be the next token after the parse returns.
    * Returns the created instance of the class.
    *
    * ALL LITERALS:
    * Will create an instance of itself with the literal value, skip their own
    * token and return to previous parse call.
    *
    */
    static AspStmt parse(Scanner s){
        Main.log.enterParser("stmt");
        as = null;
        switch(s.curToken().kind){
            case nameToken:
                if(s.peekNext().kind == leftParToken){
                    as = AspExprStmt.parse(s); break;
                }
                as = AspAssignment.parse(s); break;
            case ifToken:
                as = AspIfStmt.parse(s); break;
            case whileToken:
                as = AspWhileStmt.parse(s); break;
            case returnToken:
                as = AspReturnStmt.parse(s); break;
            case passToken:
                as = AspPassStmt.parse(s); break;
            case defToken:
                as = AspFuncDef.parse(s); break;
            default:
                as = AspExprStmt.parse(s);
        }
        Main.log.leaveParser("stmt");
        return as;
    }

    @Override
    public void prettyPrint() {
    	as.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue temp = as.eval(curScope);
        return temp;
    }
}
