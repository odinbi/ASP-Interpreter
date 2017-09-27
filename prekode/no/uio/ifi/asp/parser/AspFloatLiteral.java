class AspFloatLiteral extends AspAtom {
    //returns RuntimeValue
    public TokenKind value;
    AspFloatLiteral(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspFloatLiteral parse(Scanner s){
        Main.log.enterParser("float litteral");
        if(!s.isFloatToken()){
            parserError("Expected a floatToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspFloatLiteral abl = new AspFloatLiteral(s.curLineNum(), s.getTokenKind());
        Main.log.leaveParser("float litteral");
        return abl;
    }
}
