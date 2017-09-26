class AspBooleanLiteral extends AspAtom {
    //returns RuntimeValue
    public TokenKind value;
    AspBooleanLiteral(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspBooleanLiteral parse(Scanner s){
        Main.log.enterParser("boolean litteral");
        if(!s.isBooleanToken()){
            parserError("Expected a booleanToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum(), s.getTokenKind());
        Main.log.leaveParser("boolean litteral");
        return abl;
    }
}
