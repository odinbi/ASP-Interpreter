package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner; import no.uio.ifi.asp.scanner.TokenKind;

class AspPassStmt extends AspStmt{
    static AspPassStmt parse(Scanner s){
        Main.log.enterParser("pass stmt");
        AspPassStmt aPass = new AspPassStmt(s.curLineNum());
        skip(s, passToken);
        skip(s, newLineToken);
        Main.log.leaveParser("pass stmt");
        return aPass;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite("pass ");
        Main.log.prettyWrite("\n");
    }
}
