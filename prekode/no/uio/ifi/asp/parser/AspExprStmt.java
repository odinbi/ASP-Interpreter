class AspExprStmt extends AspStmt{

    AspExpr expr;

    static AspExprStmt parse(Scanner s){
        Main.log.enterParser("expr stmt");
        AspExprStmt aes = new AspExprStmt(s.curLineNum());
        aes.expr = AspExpr.parse(s);
        skip(s, newLineToken);
        Main.log.leaveParser("expr stmt");
        return aes;
    }

}
