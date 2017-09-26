abstact class AspPrimarySuffix extends AspSyntax {
    static AspPrimarySuffix parse(Scanner s){
        Main.log.enterParser("primary suffix");
        AspPrimarySuffix aps = null;

        switch(s.curToken().kind){
            case leftBracketToken:
                aps = AspArguments.parse(s); break;
            case leftParToken:
                aps = AspSubscription.parse(s); break;
            default:
                parserError("Expected an [ or ( but found a " +
                            s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("primary suffix");
        return aps;
    }
}
