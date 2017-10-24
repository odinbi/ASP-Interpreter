package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;

class AspPrimary extends AspSyntax {
    AspAtom atom;
    ArrayList<AspPrimarySuffix> suffixes = new ArrayList<>();

    AspPrimary(int n){
        super(n);
    }

    static AspPrimary parse(Scanner s) {
        Main.log.enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s);
        while(true){
            if (s.curToken().kind != leftParToken &&
                s.curToken().kind != leftBracketToken)
                break;
            ap.suffixes.add(AspPrimarySuffix.parse(s));
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

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //temporary solution
        System.out.println("\t@AspPrimary.eval()");
        if(suffixes.size() > 0){
            RuntimeListValue name = (RuntimeListValue)atom.eval(curScope);

            ArrayList<RuntimeValue> suffix = new ArrayList<>();

            for(AspPrimarySuffix suf : suffixes){
                suffix.add(suf.eval(curScope));
            }
            System.out.println("\t\tSTATUS: name: " + name.toString());
            RuntimeValue rtrn = null;
            for(int i = 0; i < suffix.size(); i++){
                RuntimeListValue temp = (RuntimeListValue)suffix.get(i);
                if(i < suffix.size()-1){
                    name = (RuntimeListValue)name.getEntry(temp.getEntry(0), this);
                } else{
                    rtrn = name.getEntry(temp.getEntry(0), this);
                }
            }
            System.out.println("\t/@AspPrimary.eval()");
            return rtrn;
        }
        System.out.println("\t/@AspPrimary.eval()");
        return atom.eval(curScope);
    }
}
