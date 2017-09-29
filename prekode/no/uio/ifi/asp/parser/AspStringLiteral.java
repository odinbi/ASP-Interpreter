class AspStringLiteral extends AspAtom {
    //returns RuntimeValue
    public String value;
    AspStringLiteral(int n, String s){
        this.super(n);
        this.value = s;
    }

    static AspStringLiteral parse(Scanner s){
        Main.log.enterParser("string litteral");
        if(!s.isStringToken()){
            parserError("Expected a stringToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspStringLiteral abl = new AspStringLiteral(s.curLineNum(), s.curToken.stringLit);
        Main.log.leaveParser("string litteral");
        return abl;
    }
}
