class AspPrimary extends AspSyntax {
    ArrayList<ApsPrimarySuffix> suffixes = new ArrayList<>();
    AspAtom atom;
    static AspPrimary parse(Scanner s) {
        Main.log.enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s);

        while(true){
            if (!s.isPrimarySuffix()) break; //eddit later.
            ap.suffixes.add(ApsPrimarySuffix.parse(s));
        }

        Main.log.leaveParser("primary");
        return ap;
    }

    @Override
    void prettyPrint() {
        atom.prettyPrint();
        for (AspPrimarySuffix suffix: suffixes) {
            suffix.prettyPrint();
        }
    }
}
