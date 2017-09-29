class AspName extends AspAtom{

    public String value;

    AspName(int n, String s){
        this.super(n);
        this.value = s;
    }

    static AspName parse(Scanner s){
        Main.log.enterParser("name");
        if(!s.isStringToken()){
            parserError("Expected a nameToken but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        AspName an = new AspName(s.curLineNum(), s.curToken.name);
        Main.log.leaveParser("name");
        return an;
    }
}
