package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspFloatLiteral extends AspAtom {
    //returns RuntimeValue
    public double value;
    AspFloatLiteral(int n, double value){
        super(n);
        this.value = value;
    }

    static AspFloatLiteral parse(Scanner s){
        Main.log.enterParser("float literal");
        AspFloatLiteral abl = null;

        if(s.curToken().kind != floatToken){
            parserError("Expected a floatToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        abl = new AspFloatLiteral(s.curLineNum(), s.curToken().floatLit);
        skip(s, floatToken);
        Main.log.leaveParser("float literal");
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
