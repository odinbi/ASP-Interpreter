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

    /**
    * peek
    * return type: Token
    *
    * returns current token if curLineTokens is not empty
    * else it returns an emptyToken.
    *
    * this method is added to prevent null pointer exceptions
    */
    public Token peek(){
        if(curLineTokens.size() > 0)
            return curLineTokens.get(0);
        return new Token(emptyToken);
    }

    /**
    * peekNext
    * return type: Token
    *
    * returns next token in curLineTokens without editing the list structure
    * if the list has more than 1 element.
    * else it returns an emptyToken
    *
    * this method is added so that parser calls can check tokens without having
    * to alter the list structure in cases where the parsing of a token
    * is dependent of what tokens comes after the current.
    */
    public Token peekNext(){
        if(curLineTokens.size() > 1)
            return curLineTokens.get(1);
        return new Token(emptyToken);
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
        /*
        * if-else tests to analyse the read line.
        * if line == null, the line is empty and we are on EoF.
        * if line matches ignoreLine() test the line should not be parsed by the Scanner
        * all other lines should be parsed by the Scanner
        */
        if(line == null){ //  || line == ""
            indentHandle(0);
            curLineTokens.add(new Token(eofToken, curLineNum()));
            for (Token t: curLineTokens)
            Main.log.noteToken(t);
            return;
        } else if(ignoreLine(line)){
            readNextLine();
            return;
        } else{
            String stringLine = expandLeadingTabs(line);
            int countBlank = countBlanks(stringLine.toCharArray());
            indentHandle(countBlank);
            String word = "init";
            while(word != "" && word != "\n"){
                word = getWord(line);
                handleWord(word);
            }
        }

        // Add all tokens to log:
        for (Token t: curLineTokens)
            Main.log.noteToken(t);
        //Get all tokens on next line
        //readNextLine();
        return;
    }

    /**
    * handleWord
    * returns: void
    * input paramenter: String word
    *
    * handleWord recognize the String word as separate TokenKind enums and
    * makes a new Token object with the correct TokenKind, proceeds to add
    * the new Token object to the Token stack.
    *
    */
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
                    t = new Token(floatToken, curLineNum());
                    t.floatLit = Double.parseDouble(word);
                } catch(NumberFormatException e2){
                    Main.error(e2.getMessage());
                }
            }
        } else if(word.matches("\\d+\\.?\\d+")){
            try{
                t = new Token(floatToken, curLineNum());
                t.floatLit = Double.parseDouble(word);//Float.parseFloat(word);
            } catch(NumberFormatException nfe){
                Main.error(nfe.getMessage());
            }
        } else if(word == "\n" || word == "\r"){
            t = new Token(newLineToken, curLineNum());
        } else if(word != null && word != "" && word != " " && !Character.isDigit(word.charAt(0))){
            t = new Token(nameToken, curLineNum());
            t.name = word;
        } else {
            scannerError("handleWord() could not recognize word: " + word);
        }
        curLineTokens.add(t);
    }

    /**
    * getWord
    * returns: String curWord
    * input paramenter: String stringLine
    *
    * getWord recognizes sections of the input sting as separate syntax objects
    * and splits them apparts so that the handleWord function add it to the stack.
    *
    * every time getWord returns a character it updates global variable currentIndex
    * to save the position in the current string-
    *
    * variables: String curWord, boolean inString, char currentChar
    *   curWord: concatination of characters that make up a legal statement in asp, the return value
    *   inString: default false, if processing a string litteral set to true
    *   currentChar: current char from input string processed by the function.
    *
    * getWord first checks if it is reading a string literal object,
    *   if true it will add all characters to the output untill it reaches a string end symbol.
    * If getWord is not in a string it will test if the current character appended to current word is a digit.
    *   return current word if false, append if true.
    * else if current character is an opperator or a delimiter, check if the character appended to the word is:
    *   return current word if false, append if true.
    *   if the word is empty append.
    * Else if current word is delimiter or operator, return the current word.
    * Else if character is whitespace, return current word if word is not empty
    * Else if character is a string start, set inString to true append character to string.
    * Else if none above, return character.
    *
    * UPDATE:
    * Now handles illegal string endings. String has to end with same quotation, and need a quotation in the end.
    */
    private String getWord(String stringLine){
        String curWord = "";
        boolean inString = false;
        boolean strongQuote = false;

        if (stringLine.length() <= currentIndex) return "\n";

        for (int index = currentIndex; index < stringLine.length(); ++index){

            char currentChar = stringLine.charAt(index);

        if(inString){
            if(currentChar == '\"' && !strongQuote){
                curWord += currentChar;
                currentIndex = ++index;
                return curWord;
            }
            if(strongQuote &&  currentChar == '\''){
                currentChar = '\"';
                curWord += currentChar;
                currentIndex = ++index;
                return curWord;
            }
            if(index == stringLine.length()-1 && currentChar != '\n'){
                scannerError("String literal not terminated, illegal String ending!");
            }
            curWord += currentChar;
        } else if((curWord + currentChar).matches("\\d*\\.\\d*")){
            curWord += currentChar;
        } else if(isOperator(String.valueOf(currentChar)) || isDelimiter(String.valueOf(currentChar))){
            if(curWord != ""){
                if(isDelimiter(curWord + currentChar) || isOperator(curWord + currentChar)){
                    curWord += currentChar;
                } else{
                    currentIndex = index;
                    return curWord;
                }
            } else {
                curWord += currentChar;
            }
        } else if(isDelimiter(curWord) || isOperator(curWord)){
            currentIndex = index;
            return  curWord;
        } else {
            if(currentChar == ' '){
                currentIndex = index;
                if(curWord != "") return curWord;
            } else if(currentChar == '\"' || currentChar == '\''){
                if (currentChar == '\''){
                    inString = true;
                    strongQuote = true;
                    currentChar = '\"';
                }
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
        currentIndex = ++index;
        return curWord;
    }
    }
    return curWord;
    }

    /**
    * isKeyword, isOperator, isDelimiter
    * return value: boolean
    * input parameter: String word
    *
    * checks if word is a Keyword, Operator or Delimiter respectively.
    */
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

    /**
    * ignoreLine
    * return value: boolean
    * input parameter: String input
    *
    * checks if a string is comented out or if it only consists of blanks.
    */
    public boolean ignoreLine(String input){
        boolean cmtL = input.matches("^\\s*#.*");
        boolean blnkL = input.matches("^\\s*$");
        return (cmtL || blnkL);
    }

    /**
    * indentsPush
    * return value: void
    * input parameter: int
    *
    * pushes an int to the top of the indents stack.
    * Updates global index numIndents.
    */
    private void indentsPush(int i){
        indents[numIndents] = i;
        curLineTokens.add(new Token(indentToken, curLineNum()));
        numIndents++;
    }
    /**
    * indentsPop
    * return value: void
    * input parameter: void
    *
    * pops an int from the top of the indents stack.
    * Updates global index numIndents.
    */
    private void indentsPop(){
        curLineTokens.add(new Token(dedentToken, curLineNum()));
        numIndents--;
    }
    /**
    * indentsTop
    * return value: int
    * input parameter: void
    *
    * returns the top object of the indents stack.
    */
    private int indentsTop(){
        return indents[numIndents-1];
    }
    /**
    * indentHandle
    * return value: void
    * input parameter: int blanks
    *
    * checks if input blanks is larger or lesser than the current stack top
    * and adjusts the stack after the new value of blanks.
    * calls itself in the end to properly handle dedents.
    */
    private void indentHandle(int blanks){
        if(indentsTop() < blanks){
            indentsPush(blanks);
            //add token INDENT
        }
        else if(indentsTop() > blanks){
            boolean isMatch = false;
            for(int i = 0; i < numIndents; i++){
                if(indents[i] == blanks){
                    isMatch = true;
                    break;
                }
            }
            if(!isMatch) scannerError("Indentation error!");
            indentsPop();
            //add token DEDENT
        } else return;
        indentHandle(blanks);
    }

    /**
    * countBlanks
    * return value: int
    * input parameter: char[] line
    *
    * counts the number of whitespace characters in the array untill it meats a
    * non-whitespace character.
    */
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

    /**
    * isCompOpr, isFactorOpr, isFactorPrefix
    * return value: boolean
    * optional input parameter: t <Token>
    *
    * checks if a given token is of any kind included in the
    * comparison/factor opperators/prefixes
    */

    public boolean isCompOpr() {
        TokenKind k = curToken().kind;
        TokenKind[] compOpr = {lessToken, greaterToken, doubleEqualToken,
                                greaterEqualToken, lessEqualToken, notEqualToken};
        return Arrays.asList(compOpr).contains(k);
    }

    public boolean isCompOpr(Token t) {
        TokenKind k = t.kind;
        TokenKind[] compOpr = {lessToken, greaterToken, doubleEqualToken,
                                greaterEqualToken, lessEqualToken, notEqualToken};
        return Arrays.asList(compOpr).contains(k);
    }

    public boolean isFactorPrefix() {
        TokenKind k = curToken().kind;
        return (k == plusToken || k == minusToken);
    }

    public boolean isFactorPrefix(Token t) {
        TokenKind k = t.kind;
        return (k == plusToken || k == minusToken);
    }


    public boolean isFactorOpr() {
        TokenKind k = curToken().kind;
        TokenKind[] factorOpr = {astToken, slashToken,
                                percentToken, doubleSlashToken};
        return Arrays.asList(factorOpr).contains(k);
    }

    public boolean isFactorOpr(Token t) {
        TokenKind k = t.kind;
        TokenKind[] factorOpr = {astToken, slashToken,
                                percentToken, doubleSlashToken};
        return Arrays.asList(factorOpr).contains(k);
    }

    public boolean isBoolean(){
        TokenKind k = curToken().kind;
        if(k == trueToken || k == falseToken) return true;
        return false;
    }

    public boolean isTermOpr() {
        TokenKind k = curToken().kind;
        return (k == plusToken || k == minusToken);
    }

    public boolean isTermOpr(Token t) {
        TokenKind k = t.kind;
        return (k == plusToken || k == minusToken);
    }
}
