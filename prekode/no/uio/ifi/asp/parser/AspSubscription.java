class AspSubscription extends AspPrimarySuffix{

    ArrayList<AspExpr> expr = new ArrayList<>();

    static AspSubscription parse(Scanner s){
        Main.log.enterParser("subscription");
        AspSubscription as = new AspSubscription(s.curLineNum());
        if(s.curToken().kind == leftBracketToken) skip(s, leftBracketToken);
        as.expr.add(AspExpr.parse(s));
        if(s.curToken().kind == rightBracketToken) skip(s, rightBracketToken);
        else parserError ("expected a rightBracketToken but found: " +
                            s.curToken().kind + "!" + s.curLineNum);
        Main.log.leaveParser("subscription"
        return as;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" [ ");
        expr.prettyPrint();
        Main.log.prettyWrite(" ] ");
    }
}
