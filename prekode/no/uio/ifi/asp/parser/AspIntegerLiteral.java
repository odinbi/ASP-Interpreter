class AspIntegerLiteral extends AspAtom {
    //returns RuntimeValue
    public TokenKind value;
    AspIntegerLiteral(int n, TokenKind tk){
        this.super(n);
        this.value = tk;
    }

    static AspIntegerLiteral parse(Scanner s){
        Main.log.enterParser("integer litteral");
        if(!s.isIntegerToken()){
            parserError("Expected a integerToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspIntegerLiteral abl = new AspIntegerLiteral(s.curLineNum(), s.getTokenKind());
        Main.log.leaveParser("integer litteral");
        return abl;
    }
}
