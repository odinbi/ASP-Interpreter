abstract class AspFactorOpr extends AspSyntax {
    TokenKind value;
    AspFactorOpr(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspFactorOpr parse(Scanner s) {
        Main.log.enterParser("factor opr");

        switch (s.curToken().kind) {
            case astToken:
            case slashToken:
            case doubleSlashToken:
            case percentToken:
                AspFactorOpr a = new AspFactorOpr(s.curLineNum(), s.curToken.kind());
                break;
            default:
                parserError("Expected an factor opr but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("factor opr");
        return a;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" " + value.toString());
    }
}
