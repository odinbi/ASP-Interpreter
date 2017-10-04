package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspNotTest extends AspSyntax {
    AspComparison comparison;
    int value;
    AspNotTest(int n, int value){
        super(n);
        this.value = value;
    }

    static AspNotTest parse(Scanner s) {
        Main.log.enterParser("not test");
        AspNotTest ant;
        if (s.curToken().kind == notToken){
            ant = new AspNotTest(s.curLineNum(), 1);;
        } else{
            ant = new AspNotTest(s.curLineNum(), 0);
        }
        ant.comparison = AspComparison.parse(s);

        Main.log.leaveParser("not test");
        return ant;
    }

    @Override
    void prettyPrint() {
        if (value == 1)
            Main.log.prettyWrite(" not ");
        comparison.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
