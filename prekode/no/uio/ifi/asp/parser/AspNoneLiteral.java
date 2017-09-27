class AspNoneLiteral extends AspAtom {
    //returns RuntimeValue
    public TokenKind value;
    AspNoneLiteral(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspNoneLiteral parse(Scanner s){
        Main.log.enterParser("none litteral");
        if(!s.isNoneToken()){
            parserError("Expected a noneToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspNoneLiteral abl = new AspNoneLiteral(s.curLineNum(), s.getTokenKind());
        Main.log.leaveParser("none litteral");
        return abl;
    }
}
