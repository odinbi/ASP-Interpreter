class AspInnerExpr extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();
    static AspInnerExpr parse(Scanner s){
        Main.log.enterParser("inner expr");
        s.skip(s, leftParToken);
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
        aie.expr.add(AspExpr.parse(s));
        s.skip(s, rightParToken);
        Main.log.leaveParser("inner expr");
        return aie;
    }
}
