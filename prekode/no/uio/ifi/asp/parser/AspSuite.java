class AspSuite extends AspSyntax{

    ArrayList<AspStmt> stmt = new ArrayList<>();

    static AspSuite parse(Scanner s){
        Main.log.enterParser("suite");
        AspSuite suite = new AspSuite(s.curLineNum());
        skip(s, newLineToken);
        skip(s, indentToken);
        while(true){
            suite.stmt.add(AspStmt.parse(s));
            if(s.curToken().kind == dedentToken) break;
        }
        skip(s, dedentToken);
        Main.log.leaveParser("suite");
        return suite;
    }
}
