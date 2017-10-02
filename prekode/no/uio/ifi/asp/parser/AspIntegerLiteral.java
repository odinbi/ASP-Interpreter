class AspIntegerLiteral extends AspAtom {
    //returns RuntimeValue
    public int value;
    AspIntegerLiteral(int n, int value){
        this.super(n);
        this.value = value;
    }

    static AspIntegerLiteral parse(Scanner s){
        Main.log.enterParser("integer litteral");
        if(!s.isIntegerToken()){
            parserError("Expected a integerToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspIntegerLiteral abl = new AspIntegerLiteral(s.curLineNum(), s.curToken.integerLit);
        Main.log.leaveParser("integer litteral");
        return abl;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite(" " + value + " ");
    }
}
