class AspReturnStmt extends AspStmt{

    AspExpr expr;

    static AspReturnStmt parse(Scanner s){
        Main.log.enterParser("return stmt");
        AspReturnStmt rtrn = new AspReturnStmt(s.curLineNum());
        skip(s, returnToken);
        rten.expr = AspExpr.parse(s);
        skip(s, newLineToken);
        Main.log.leaveParser("return stmt");
        return rtrn;
    }

}
