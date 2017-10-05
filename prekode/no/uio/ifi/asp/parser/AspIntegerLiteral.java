package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspIntegerLiteral extends AspAtom {
    //returns RuntimeValue
    public long value;
    AspIntegerLiteral(int n, long value){
        super(n);
        this.value = value;
    }

    static AspIntegerLiteral parse(Scanner s){
        Main.log.enterParser("integer litteral");
        if(s.curToken().kind != integerToken){
            parserError("Expected a integerToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspIntegerLiteral abl = new AspIntegerLiteral(s.curLineNum(),
                                                s.curToken().integerLit);
        Main.log.leaveParser("integer litteral");
        skip(s, integerToken);
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
