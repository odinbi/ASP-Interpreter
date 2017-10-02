class AspFloatLiteral extends AspAtom {
    //returns RuntimeValue
    public float value;
    AspFloatLiteral(int n, float value){
        this.super(n);
        this.value = value;
    }

    static AspFloatLiteral parse(Scanner s){
        Main.log.enterParser("float litteral");
        if(!s.isFloatToken()){
            parserError("Expected a floatToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspFloatLiteral abl = new AspFloatLiteral(s.curLineNum(), s.curToken.floatLit);
        Main.log.leaveParser("float litteral");
        return abl;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite(" " + value + " ");
    }
}
