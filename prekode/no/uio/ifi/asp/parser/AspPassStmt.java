class AspPassStmt extends AspStmt{
    static AspPassStmt parse(Scanner s){
        Main.log.enterParser("pass stmt");
        AspPassStmt aPass = new AspPassStmt(s.curLineNum());
        skip(s, passToken);
        skip(s, newLineToken);
        Main.log.leaveParser("pass stmt");
        return aPass;
    }

    @Override
    public void prettyPrint() {
    	Main.log.prettyWrite("pass ");
        Main.log.prettyWrite("\n");
    }
}
