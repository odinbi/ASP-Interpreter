class AspIfStmt extends AspStmt{

    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspSuite> suite = new ArrayList<>();

    static AspIfStmt parse(Scanner s){
        Main.log.enterParser("if stmt");
        AspIfStmt ais = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);

        while(true){
            ais.expr.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));

            if (s.curToken().kind != elifToken){
                break;
            } else{
                skip(s, elifToken);
            }
        }

        if (s.curToken().kind == elseToken){
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));
        }
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("if ");
        int nElif = -1;
    	for(AspExpr exp, AspSuite ste) in zip(expr, suite){
            nElif++;
            if(nElif > 0 && nElif != expr.size()-1) Main.log.prettyWrite(" elif ");
            else if(nElif == expr.size()-1) Main.log.prettyWrite(" else ");
            exp.prettyPrint();
            Main.log.prettyWrite(" : ");
            ste.prettyPrint();
        }
    }
}
