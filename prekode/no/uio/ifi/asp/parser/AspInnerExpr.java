class AspInnerExpr extends AspAtom {

    AspExpr expr;

    static AspInnerExpr parse(Scanner s){
        Main.log.enterParser("inner expr");

        s.skip(s, leftParToken);
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
        aie.expr = AspExpr.parse(s);
        s.skip(s, rightParToken);

        Main.log.leaveParser("inner expr");
        return aie;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" ( ");
        expr.prettyPrint();
        Main.log.prettyWrite(" ) ");
    }
}
