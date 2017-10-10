package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspBooleanLiteral extends AspAtom {
    public boolean value;
    AspBooleanLiteral(int n, boolean bool){
        super(n);
        this.value = bool;
    }

    /**
    * parse
    * returns: AspBooleanLiteral
    * input: Scanner
    *
    * parse takes a Scanner s object, returns a new instance of the class with
    * the literal value from the token.
    * Sets the Scanner token list pointer to next token.
    */
    static AspBooleanLiteral parse(Scanner s){
        Main.log.enterParser("boolean literal");

        if(!s.isBoolean()){
            parserError("Expected a booleanToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }

        AspBooleanLiteral abl;

        if(s.curToken().kind == trueToken){
            abl = new AspBooleanLiteral(s.curLineNum(), true);
            skip(s, trueToken);
        } else{
            abl = new AspBooleanLiteral(s.curLineNum(), false);
            skip(s, falseToken);
        }
        Main.log.leaveParser("boolean literal");
        return abl;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite("" + value);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
