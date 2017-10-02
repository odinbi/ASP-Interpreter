class AspTerm extends AspSyntax {
    ArrayList<ApsFactor> factor = new ArrayList<>();
    ArrayList<AspTermOpr> oprs = new ArrayList<>();
    static AspTerm parse(Scanner s) {
        Main.log.enterParser("term");
        AspTerm at = new AspTerm(s.curLineNum());
        while(true){
            at.factor.add(AspFactor.parse(s));
            if (!s.isTermOpr()) break;
            at.oprs.add(AspTermOpr.parse(s));
        }

        Main.log.leaveParser("term");
        return at;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspFactor fac: factor) {
            if (nPrinted > 0)
                oprs[nPrinted].prettyPrint();
            fac.prettyPrint(); ++nPrinted;
        }
    }
}
