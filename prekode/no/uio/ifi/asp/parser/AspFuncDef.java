class AspFuncDef extends AspStmt{
    ArrayList<AspName> name = new ArrayList<>();
    AspSuite suite;
    static AspFuncDef parse(Scanner s){
        Main.log.enterParser("func def");
        AspFuncDef func = new AspFuncDef(s.curLineNum());
        skip(s, defToken);
        func.name.add(AspName.parse(s));
        skip(s, leftParToken);
        if(s.curToken().kind != rightParToken){
            while(true){
                func.name.add(AspName.parse(s));
                if(s.curToken().kind != commaToken) break;
                skip(s, commaToken);
            }
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        func.suite = AspSuite.parse(s);
        Main.log.leaveParser("func def");
        return func;
    }

}
