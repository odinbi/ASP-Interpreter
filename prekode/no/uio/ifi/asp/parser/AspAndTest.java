package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

class AspAndTest extends AspSyntax {

    AspAndTest(int n){
        super(n);
    }

    ArrayList<AspNotTest> notTests = new ArrayList<>();

    /**
    * parse
    * returns: AspAndTest
    * input: Scanner
    *
    * parse takes a Scanner s object, sends s to AspNotTest.parse,
    * expects AspNotTest.parse to traverse Scanner token list by one token.
    * checks if current token is an "andToken", continue loop if true. 
    *
    */
    static AspAndTest parse(Scanner s) {
        Main.log.enterParser("and test");
        AspAndTest aat = new AspAndTest(s.curLineNum());
        while (true) {
            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken) break;
            skip(s, andToken);
        }
        Main.log.leaveParser("and test");
        return aat;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspNotTest ant: notTests) {
            if (nPrinted > 0)
            Main.log.prettyWrite(" and ");
            ant.prettyPrint(); ++nPrinted;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
