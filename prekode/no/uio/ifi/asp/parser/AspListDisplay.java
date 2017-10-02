class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();
    static AspListDisplay parse(Scanner s){
        Main.log.enterParser("list display");
        AspListDisplay ald = new AspListDisplay(s.curLineNum());
        while (true) {
            ald.expr.add(AspExpr.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        Main.log.leaveParser("list display");
        return ald;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" [ ");
        int nComma = 0;
        for(AspExp exp : expr){
            if(nComma > 0) Main.log.prettyWrite(" , ");
            exp.prettyPrint();
            nComma++;
        }
        Main.log.prettyWrite(" ] ");
    }
}
