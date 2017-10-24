package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspSuite extends AspSyntax{
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspSuite(int n){
        super(n);
    }

    static AspSuite parse(Scanner s){
       Main.log.enterParser("suite");
       AspSuite suite = new AspSuite(s.curLineNum());
       skip(s, newLineToken);
       skip(s, indentToken);
       while(true){
           suite.stmts.add(AspStmt.parse(s));
           if(s.curToken().kind == dedentToken){
               skip(s, dedentToken);
               break;
           }
       }
       Main.log.leaveParser("suite");
       return suite;
   }

    @Override
    public void prettyPrint() {
        Main.log.prettyWriteLn();
        for(AspStmt stmt : stmts){
            Main.log.prettyIndent();
            stmt.prettyPrint();
            Main.log.prettyDedent();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        Main.rlog.enterEval("AspSuite");
        for(AspStmt stmt : stmts){
            stmt.eval(curScope);
        }
        Main.rlog.leaveEval("AspSuite");
        return null;
    }
}
