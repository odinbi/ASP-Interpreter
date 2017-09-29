class AspArguments extends AspPrimarySuffix{
    public ArrayLisy<AspExpr> aspExpr= new ArrayLisy<>();

    static AspArguments parse(Scanner s){
        Main.log.enterParser("arguments");
        AspArguments aa = new AspArguments(s.curLineNum());
        if(s.curToken().kind == leftParToken) skip(s, leftParToken);
        while(true){
            aa.aspExpr.add(AspExpr.parse(s));
            if(s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        if(s.curToken().kind == rightParToken){
            skip(s, rightParToken);
        }
        else parserError("expected rightParToken but found: " +
                        s.curToken().kind + "! " + s.curLineNum());
        Main.log.leaveParser("arguments");
    }

}
