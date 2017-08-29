package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
    private int numIndents = 0;
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

  if (ignoreLine(line)){
    readNextLine();
  }

  /*
  For hver linje:
  1) Sett en teller n til 0.
  2) For hvert tegn i linjen:
  Hvis tegnet er en blank, øk n med 1.
  Hvis tegnet er en TAB, erstatt den med 4 − (n mod 4) blanke; øk n
  tilsvarende.
  Ved alle andre tegn avsluttes denne løkken.
  */

  int countBlank = 0;
  char[] charLineArray = line.toCharArray();
  for (int i = 0; i < charLineArray.length; i++){
    if (charLineArray[i] == ' '){
      countBlank++;
    } else if (charLineArray[i] == '\t'){
        int newBlanks = tabDist - (countBlank % tabDist);
        char[] emptyArray = new char[newBlanks - 1];
        charLineArray[i] = ' ';
        for (int j = 0; j < emptyArray.length; j++){
          emptyArray[j] = ' ';
        }
        charLineArray = (emptyArray.toString() +
              charLineArray.toString().substring(i, charLineArray.length - 1)).toCharArray();

        countBlank += newBlanks;
        i += newBlanks - 1;
      }
    break;
  }

  indentHandle(countBlank);

/*	//-- Must be changed in part 1:zzz
  Håndtering av indentering
1) Opprett en stakk Indents.
2) Push verdien 0 på Indents.
3) For hver linje:z
    (a) Hvis linjen bare inneholder blanke (og eventuelt en kommentar),
    ignoreres den.
    (b) Omform alle innledende TAB-er til blanke.
    (c) Tell antall innledende blanke: n.
    (d) Hvis n > Indents.top:
    i. Push n på Indents.
    ii. Legg et ‘INDENT’-symbol i curLineTokens.
    Hvis n < Indents.top:
    Så lenge n < Indents.top:
    i. Pop Indents.
    ii. Legg et ‘DEDENT’-symbol i curLineTokens.
    Hvis nå n≠Indents.top, har vi indeteringsfeil.
4) Etter at siste linje er lest:
(a) For alle verdier på Indents som er > 0, legg et ‘DEDENT’-symbol i
curLineTokens.*/


	// Terminate line:
	curLineTokens.add(new Token(newLineToken,curLineNum()));

	for (Token t: curLineTokens)
	    Main.log.noteToken(t);
    }

    public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
    }

  Pattern comentLine = Pattern.copile("^\\s*#");
  Pattern blankLine = Pattern.compile("^\\s*$");

  public boolean ignoreLine(String input){
      Matcher cmtL = comentLine.matcher(s);
      Matcher blnkL = blankLine.matcher(s);
      return (cmtL.matches() || blnkL.matches());
  }

  private void indentsPush(int i){
     indents[numIndents] = i;
     numIndents++;
  }
  private void identsPop(){
     numIndents--;
  }
  private int indentsTop(){
     return indents[numIndents-1];
  }

  private void indentHandle(int blanks){
      if(indentsTop() < blanks){
          indentPush(blanks);
          //add token INDENT
      }
      else if(indentsTop() > blanks){
          indentPop();
          //add token DEDENT
      }
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
