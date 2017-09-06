package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.NumberFormatException;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
    private int numIndents = 0;
    private int currentIndex = 0;
    private final int tabDist = 4;


    public Scanner(String fileName) {
        curFileName = fileName;
        indents[0] = 0;  numIndents = 1;

        try {
            sourceFile = new LineNumberReader(
            new InputStreamReader(
            new FileInputStream(fileName),
            "UTF-8"));
        } catch (IOException e) {
            scannerError("Cannot read " + fileName + "!");
        }
    }


    private void scannerError(String message) {
        String m = "Asp scanner error";
        if (curLineNum() > 0)
        m += " on line " + curLineNum();
        m += ": " + message;

        Main.error(m);
    }


    public Token curToken() {
        while (curLineTokens.isEmpty()) {
            readNextLine();
        }
        return curLineTokens.get(0);
    }


    public void readNextToken() {
        if (! curLineTokens.isEmpty())
        curLineTokens.remove(0);
    }


    public boolean anyEqualToken() {
        for (Token t: curLineTokens) {
            if (t.kind == equalToken) return true;
        }
        return false;
    }


    private void readNextLine() {
        currentIndex=0;
        curLineTokens.clear();

        // Read the next line:
        String line = null;
        try {
            line = sourceFile.readLine();

            if (line == null) {
                sourceFile.close();
                sourceFile = null;
            } else {
                Main.log.noteSourceLine(curLineNum(), line);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }

        //-- Must be changed in part 1:zzz
        if(line == null){ //  || line == ""
            System.out.println("End of file");
            curLineTokens.add(new Token(eofToken, curLineNum()));
            for (Token t: curLineTokens)
                Main.log.noteToken(t);
            return;
        } else if(ignoreLine(line)){
            readNextLine();
            return;
        } else{
            System.out.println("In readNextLine - parse: " + line);
            String stringLine = expandLeadingTabs(line);
            int countBlank = countBlanks(stringLine.toCharArray());
            indentHandle(countBlank);
            String word = "init";
            while(word != "" && word != "\n"){
                word = getWord(line);
                System.out.println("In readNextLine - word fetched: " + word);
                handleWord(word);
                System.out.println("In readNextLine - word handleled: " + word);
            }
        }

        // Add all tokens to log:
        for (Token t: curLineTokens)
            Main.log.noteToken(t);
        //Get all tokens on next line
        readNextLine();
        return;
    }

    private void handleWord(String word){
        Token t = null;
        if(isKeyword(word) || isDelimiter(word) || isOperator(word)){
            t = new Token(TokenKind.getTokenKind(word), curLineNum());
        } else if(word.startsWith("\"") && word.endsWith("\"")){
            t = new Token(stringToken, curLineNum());
            t.stringLit = word.substring(1, word.length()-1);
        } else if(word.matches("\\d*")){
            try{
                int n = Integer.parseInt(word);
                t = new Token(integerToken, curLineNum());
                t.integerLit = n;
            } catch(NumberFormatException e1){
                try {
                    float f = Float.parseFloat(word);
                    t = new Token(floatToken, curLineNum());
                    t.floatLit = f;
                } catch(NumberFormatException e2){
                    Main.error(e2.getMessage());
                }
            }
        } else if(word.matches("\\d*\\.?\\d*")){
            t = new Token(floatToken, curLineNum());
            t.floatLit = Float.parseFloat(word);
        } else if(word == "\n" || word == "\r"){
            t = new Token(newLineToken, curLineNum());
        } else if(word != null && word != "" && word != " "){
            t = new Token(nameToken, curLineNum());
            t.name = word;
        } else {
            Main.error("handleWord error! Could not recognize word: " + word + ", at line: " + curLineNum());
        }
        curLineTokens.add(t);
    }

    private String getWord(String stringLine){
        String curWord = "";
        boolean inString = false;

        if (stringLine.length() <= currentIndex) return "\n";

        for (int index = currentIndex; index < stringLine.length(); ++index){

            char currentChar = stringLine.charAt(index);

            //System.out.println("word: \"" + curWord + "\" char: \'" + currentChar + "\'");
            //System.out.println("input length: " + stringLine.length() + " index: " + index);
            if(inString){
                if(currentChar == '\"'){ // '\"' = 34
                    curWord += currentChar;
                    currentIndex = ++index;
                    return curWord;
                }
                curWord += currentChar;
            } else if(isOperator(String.valueOf(currentChar)) || isDelimiter(String.valueOf(currentChar))){
                System.out.println("opperator/delimiter reached: " + currentChar);
                if(curWord != ""){
                    System.out.println("Is this an opperator? " + curWord + currentChar);
                    if(isDelimiter(curWord + currentChar) || isOperator(curWord + currentChar)){
                        curWord += currentChar;
                    } else{
                        currentIndex = index;
                        return curWord;
                    }
                } else {
                    //currentIndex = index;
                    curWord += currentChar;
                }
            } else if(isDelimiter(curWord) || isOperator(curWord)){
                currentIndex = index;
                return  curWord;
            } else {
                if(currentChar == ' '){
                    currentIndex = index;
                    if(curWord != "") return curWord;
                } else if(currentChar == '\"'){ // '\"' = 34 ascii character
                    if(curWord == ""){
                        inString = true;
                        curWord += currentChar;
                    } else{
                        currentIndex = index;
                        return curWord;
                    }
                } else{
                    curWord += currentChar;
                }
            }
            if(index == stringLine.length()-1){
                System.out.println("returns: " + curWord);
                currentIndex = ++index;
                return curWord;
            }
        }
        return curWord;
    }

    private boolean isKeyword(String word){
        String[] keyArray = {"and", "as", "assert", "break", "class", "continue", "def", "del", "elif", "else",
        "except", "False", "finally", "for", "from", "global", "if", "import", "in", "is", "lambda", "None",
        "nonlocal", "not", "or", "pass", "raise", "return", "True", "try", "while", "with", "yield"};
        return Arrays.asList(keyArray).contains(word);
    }

    private boolean isOperator(String word){
        String[] operatorsArray = {"&", "*", "|", "**", "==", "<<", ">>", "//", "<",
        ">", "<=", ">=", "^", "-", "!=", "%", "+", "/", "~"};
        return Arrays.asList(operatorsArray).contains(word);
    }

    private boolean isDelimiter(String word){
        String[] delimitersArray = {"&=", "*=", "@", "|=", ":", ",", ".", "**==",
        "<<=", ">>=", "//=", "=", "^=", "{", "[", "(", "-=",
        "%=", "+=", "}", "]", ")", ";", "/="};
        return Arrays.asList(delimitersArray).contains(word);
    }


    public int curLineNum() {
        return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
        int indent = 0;

        while (indent<s.length() && s.charAt(indent)==' ') indent++;
        return indent;
    }

    //Pattern commentLine = Pattern.compile("^\\s*#");
    //Pattern blankLine = Pattern.compile("^\\s*$");


    public boolean ignoreLine(String input){
        boolean cmtL = input.matches("^\\s*#.*");
        boolean blnkL = input.matches("^\\s*$");
        return (cmtL || blnkL);
    }

    private void indentsPush(int i){
        indents[numIndents] = i;
        curLineTokens.add(new Token(indentToken, curLineNum()));
        numIndents++;
    }
    private void indentsPop(){
        curLineTokens.add(new Token(dedentToken, curLineNum()));
        numIndents--;
    }
    private int indentsTop(){
        return indents[numIndents-1];
    }

    private void indentHandle(int blanks){
        if(indentsTop() < blanks){
            indentsPush(blanks);
            //add token INDENT
        }
        else if(indentsTop() > blanks){
            indentsPop();
            //add token DEDENT
        }
    }

    private int countBlanks(char[] line){
        int numberOfBlanks = 0;
        for (int i = 0; i < line.length; i++){
            if (line[i] == ' '){
                numberOfBlanks++;
            } else{
                return numberOfBlanks;
            }
        }
        return -1;
    }

    private String expandLeadingTabs(String s) {
        String newS = "";
        for (int i = 0;  i < s.length();  i++) {
            char c = s.charAt(i);
            if (c == '\t') {
                do {
                    newS += " ";
                } while (newS.length()%tabDist != 0);
            } else if (c == ' ') {
                newS += " ";
            } else {
                newS += s.substring(i);
                break;
            }
        }
        return newS;
    }


    private boolean isLetterAZ(char c) {
        return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
        return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }


    public boolean isFactorPrefix() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }


    public boolean isFactorOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }


    public boolean isTermOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }
}
