class AspPrimary extends AspSyntax {
    ArrayList<ApsAtom> atom = new ArrayList<>();
    static AspPrimary parse(Scanner s) {
        Main.log.enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom.add(AspAtom.parse(s));

        while(true){
            if (!s.isPrimarySuffix()) break; //eddit later.
            skip(s, primaryToken); //give propper token later!!!
        }

        Main.log.leaveParser("primary");
        return ap;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspPrimary ap: atom) {
            if (nPrinted > 0)
            //Main.log.prettyWrite(" not ");
            ap.prettyPrint(); ++nPrinted;
        }
    }
}
