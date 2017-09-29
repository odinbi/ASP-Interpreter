class AspNoneLiteral extends AspAtom {

    public NoneType value;

    AspNoneLiteral(int n){
        this.super(n);
        this.value = NONE;
    }

    static AspNoneLiteral parse(Scanner s){
        Main.log.enterParser("none litteral");
        if(!s.isNoneToken()){
            parserError("Expected a noneToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        Main.log.leaveParser("none litteral");
        return anl;
    }
}
