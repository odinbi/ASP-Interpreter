class AspStringLiteral extends AspAtom {
    //returns RuntimeValue
    public TokenKind value;
    AspStringLiteral(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspStringLiteral parse(Scanner s){
        Main.log.enterParser("string litteral");
        if(!s.isStringToken()){
            parserError("Expected a stringToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspStringLiteral abl = new AspStringLiteral(s.curLineNum(), s.getTokenKind());
        Main.log.leaveParser("string litteral");
        return abl;
    }
}
