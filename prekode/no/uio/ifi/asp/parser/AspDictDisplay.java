class AspDictDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspStringLiteral> str = new ArrayList<>();
    static AspDictDisplay parse(Scanner s){
        Main.log.enterParser("dict display");
        AspDictDisplay a = new AspDictDisplay(s.curLineNum());
        while (true) {
            if(s.curToken.kind() == stringToken){
                a.str.add(AspStringLiteral.parese(s));
                skip(s, colonToken);
                a.expr.add(AspExpr.parse(s));
            }
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        Main.log.leaveParser("dict display");
        return a;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" { ");
        int nComma = 0;
        for(AspStringLiteral asl, AspExp exp) in zip(str, expr){
            if(nComma > 0) Main.log.prettyWrite(" , ");
            asl.prettyPrint();
            Main.log.prettyWrite(" : ");
            exp.prettyPrint();
            nComma++;
        }
        Main.log.prettyWrite(" } ");
    }
}
