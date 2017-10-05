package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspTermOpr extends AspSyntax {
    TokenKind value;

    AspTermOpr(int n, TokenKind tk){
        super(n);
        this.value = tk;
    }

    static AspTermOpr parse(Scanner s) {
        Main.log.enterParser("term opr");
        AspTermOpr termOpr = null;

        switch (s.curToken().kind) {
            case plusToken:
            case minusToken:
                termOpr = new AspTermOpr(s.curLineNum(), s.curToken().kind);
                skip(s, s.curToken().kind);
                break;
            default:
                parserError("Expected an term opr but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("term opr");
        return termOpr;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" " + value.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
