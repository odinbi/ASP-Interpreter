class AspTerm extends AspSyntax {
    ArrayList<ApsFactor> factor = new ArrayList<>();
    static AspTerm parse(Scanner s) {
        Main.log.enterParser("term");
        AspTerm at = new AspTerm(s.curLineNum());
        while(true){
            at.factor.add(AspFactor.parse(s));
            if (!s.isTermOpr()) break;
            skip(s, termToken); //give propper token later!!!
        }

        Main.log.leaveParser("term");
        return at;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspTerm at: factor) {
            if (nPrinted > 0)
            //Main.log.prettyWrite(" not ");
            at.prettyPrint(); ++nPrinted;
        }
    }
}
