abstract class AspTermOpr extends AspSyntax {
    TokenKind value;
    AspTermOpr(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspTermOpr parse(Scanner s) {
        Main.log.enterParser("term opr");

        switch (s.curToken().kind) {
            case plusToken:
            case minusToken:
                AspTermOpr a = new AspTermOpr(s.curLineNum(), s.curToken.kind());
                break;
            default:
                parserError("Expected an term opr but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("term opr");
        return a;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" " + value.toString() + " ");
    }
}
