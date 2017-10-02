class AspWhileStmt extends AspStmt{

    AspExpr expr;
    AspSuite suite;

    static AspWhileStmt parse(Scanner s){
        Main.log.enterParser("while stmt");
        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken);
        aws.expr = AspExpr.parse(s);
        skip(s, colonToken);
        aws.suite = AspSuite.parse(s);
        Main.log.leaveParser("while stmt");
        return aws;
    }

}
