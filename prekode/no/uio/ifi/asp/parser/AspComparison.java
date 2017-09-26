class AspComparison extends AspSyntax {
    ArrayList<ApsTerm> term = new ArrayList<>();
    static AspComparison parse(Scanner s) {
        Main.log.enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
        while(true){
            ac.term.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            skip(s, comparisonToken); //give propper token later!!!!
        }

        Main.log.leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspComparison ac: term) {
            if (nPrinted > 0)
            //Main.log.prettyWrite(" not ");
            ac.prettyPrint(); ++nPrinted;
        }
    }
}
