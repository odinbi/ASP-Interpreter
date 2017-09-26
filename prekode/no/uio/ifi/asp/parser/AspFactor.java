class AspFactor extends AspSyntax {
    ArrayList<ApsPrimary> primary = new ArrayList<>();
    static AspFactor parse(Scanner s) {
        Main.log.enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());

        af.primary.add(AspPrimary.parse(s));
        if (s.isFactorPrefix()){
            skip(s, factorPrefixToken); //give propper token later!!!
        }

        while(true){
            af.primary.add(AspPrimary.parse(s));
            if (!s.isFactorOpr()) break;
            skip(s, factorToken); //give propper token later!!!
        }

        Main.log.leaveParser("factor");
        return af;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspFactor af: primary) {
            if (nPrinted > 0)
            //Main.log.prettyWrite(" not ");
            af.prettyPrint(); ++nPrinted;
        }
    }
}
