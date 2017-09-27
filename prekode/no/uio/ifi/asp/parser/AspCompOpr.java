abstract class AspCompOpr extends AspSyntax {
    TokenKind value;
    AspCompOpr(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspCompOpr parse(Scanner s) {
        Main.log.enterParser("comp opr");

        switch (s.curToken().kind) {
            case lessToken:
            case greaterToken:
            case doubleEqualToken:
            case greaterEqualToken
            case lessEqualToken:
            case notEqualToken:
                AspCompOpr a = new AspCompOpr(s.curLineNum(), s.curToken.kind());
                break;
            default:
                parserError("Expected an comp opr but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("comp opr");
        return a;
    }
}
