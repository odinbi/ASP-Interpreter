package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;  import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

class AspAndTest extends AspSyntax {

    AspAndTest(int n){
        super(n);
    }

    ArrayList<AspNotTest> notTests = new ArrayList<>();

    static AspAndTest parse(Scanner s) {
        System.out.println("curToken: " + s.curToken().kind.toString());
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
