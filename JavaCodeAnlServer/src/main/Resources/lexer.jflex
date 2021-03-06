/* SECTION 1: USER CODE */
package Backend.Objects.Lexers;


import java_cup.runtime.*;
import java.util.ArrayList;
import java.util.HashMap;
import Backend.Objects.Parsers.sym;
import Backend.Objects.AnalysisError;

/* SECTION 2: CONFIG */
%%
%class JavaCodeLexer
%type java_cup.runtime.Symbol
%cup
%column
%full
%unicode
%line
%public

// define language
WHITESPACES=[\s\t\r]+
NUM=[0-9]+
LETT=[a-zA-ZáéíóúÁÉÍÓÚñÑ]+
VISIB=(private|public|protected)
VAR_TYPE=(int|boolean|String|char|double) // add OBject type, but this is an ID
FUN_TYPE=(void|{VAR_TYPE})
LINE_COMM=(\/\/[^\n\r]*)
//LONG_COMM=(\/\*[^*]*\*+(\?\:[^\/\*][^\*]*\*+)*\/)
LONG_COMM=(\/\*([^\*]|[\r\n]|(\*+([^\*\/]|[\r\n])))*\*+\/)
VAL_COMILL=("\""[^\"\n\r]*"\"")
SING_LETT=('[a-zA-ZáéíóúÁÉÍÓÚñÑ]')
ID=({LETT}({LETT}|{NUM}|"_")*)

%{

    private final ArrayList<String> comments = new ArrayList<>();
    private int commentsCounter = 0;
    private final HashMap<String, Integer> commentsDeclTimes = new HashMap<>();
    private final ArrayList<AnalysisError> errors = new ArrayList<>();
    private String filename,  project;

    public void saveComment(String comment, boolean isLong) {
        if(isLong) {
            comment = comment.substring(2, comment.length() - 2).trim();
        } else {
            comment = comment.substring(2, comment.length()).trim();
        }
        this.comments.add(comment.trim()); 
        this.commentsCounter++;

        // check if exists
        if (!commentsDeclTimes.containsKey(comment)) {
            commentsDeclTimes.put(comment, 1);
        } else {
            commentsDeclTimes.put(comment, commentsDeclTimes.get(comment) + 1);
        }
    }

    private void incCommentsCounter() {this.commentsCounter++;}
    public void setFilename(String filename, String project){ this.filename = filename; this.project = project;}

    public ArrayList<String> getComments () { return this.comments; }
    public ArrayList<AnalysisError> getErrors() { return this.errors; }
    public int getCommentsCounter() { return this.commentsCounter; }
    public HashMap<String, Integer> getHashComments() { return this.commentsDeclTimes; }
    public void addError() { errors.add(new AnalysisError(yyline + 1, yycolumn + 1, yytext(), this.filename, this.project, "LEXICO" )); }
%}


%%

/* SECTION 3: LEXICAL RULES */

// IGNORE
// whitespaces
{WHITESPACES}       {/* ignore */}

// SYMBOLS
// split
(".")               {return new Symbol(sym.DOT, yyline+1, yycolumn+1, yytext());}
(",")               {return new Symbol(sym.COMMA, yyline+1, yycolumn+1, yytext());}
//  arithm
("+")               {return new Symbol(sym.PLUS, yyline+1, yycolumn+1, yytext());}
("-")               {return new Symbol(sym.MINUS, yyline+1, yycolumn+1, yytext());}
("/")               {return new Symbol(sym.DIV, yyline+1, yycolumn+1, yytext());}
("%")               {return new Symbol(sym.MOD, yyline+1, yycolumn+1, yytext());}
("*")               {return new Symbol(sym.STAR, yyline+1, yycolumn+1, yytext());}
//  relational
(">=")              {return new Symbol(sym.GE, yyline+1, yycolumn+1, yytext());}
(">")               {return new Symbol(sym.GT, yyline+1, yycolumn+1, yytext());}
("<=")              {return new Symbol(sym.LE, yyline+1, yycolumn+1, yytext());}
("<")               {return new Symbol(sym.LT, yyline+1, yycolumn+1, yytext());}
// assign
("=")               {return new Symbol(sym.ASSIGN, yyline+1, yycolumn+1, yytext());}
("==")              {return new Symbol(sym.EQ, yyline+1, yycolumn+1, yytext());}
("!=")              {return new Symbol(sym.NE, yyline+1, yycolumn+1, yytext());}
// increase and decrease
("++")              {return new Symbol(sym.INC, yyline+1, yycolumn+1, yytext());}
("--")              {return new Symbol(sym.DEC, yyline+1, yycolumn+1, yytext());}
("*=")              {return new Symbol(sym.MULT_T, yyline+1, yycolumn+1, yytext());}
("/=")              {return new Symbol(sym.DIV_T, yyline+1, yycolumn+1, yytext());}
("+=")              {return new Symbol(sym.PLUS_T, yyline+1, yycolumn+1, yytext());}
("-=")              {return new Symbol(sym.LESS_T, yyline+1, yycolumn+1, yytext());}
("!")               {return new Symbol(sym.NOT, yyline+1, yycolumn+1, yytext());}
//  logical
("&&")              {return new Symbol(sym.AND, yyline+1, yycolumn+1, yytext());}
("||")              {return new Symbol(sym.OR, yyline+1, yycolumn+1, yytext());}
// group
("{")               {return new Symbol(sym.BRAC_OPEN, yyline+1, yycolumn+1, yytext());}
("}")               {return new Symbol(sym.BRAC_CLOSE, yyline+1, yycolumn+1, yytext());}
("(")               {return new Symbol(sym.PAR_OPEN, yyline+1, yycolumn+1, yytext());}
(")")               {return new Symbol(sym.PAR_CLOSE, yyline+1, yycolumn+1, yytext());}
// end 
(";")               {return new Symbol(sym.SEMICOLON, yyline+1, yycolumn+1, yytext());}
(":")               {return new Symbol(sym.COLON, yyline+1, yycolumn+1, yytext());}

// METHODS
// import
(import)            {return new Symbol(sym.IMPORT, yyline+1, yycolumn+1, yytext());}
// accessibility type
{VISIB}             {return new Symbol(sym.VISIBILITY, yyline+1, yycolumn+1, yytext());}
// class
(class)             {return new Symbol(sym.CLASS, yyline+1, yycolumn+1, yytext());}
// functions
(if)                {return new Symbol(sym.IF, yyline+1, yycolumn+1, yytext());}
(else)              {return new Symbol(sym.ELSE, yyline+1, yycolumn+1, yytext());}
(for)               {return new Symbol(sym.FOR, yyline+1, yycolumn+1, yytext());}
(while)             {return new Symbol(sym.WHILE, yyline+1, yycolumn+1, yytext());}
(do)                {return new Symbol(sym.DO, yyline+1, yycolumn+1, yytext());}
(switch)            {return new Symbol(sym.SWITCH, yyline+1, yycolumn+1, yytext());}
(package)           {return new Symbol(sym.PACKAGE, yyline+1, yycolumn+1, yytext());}
// other method access
(static)            {return new Symbol(sym.STATIC, yyline+1, yycolumn+1, yytext());}
(final)             {return new Symbol(sym.FINAL, yyline+1, yycolumn+1, yytext());}
(new)               {return new Symbol(sym.NEW, yyline+1, yycolumn+1, yytext());}
(case)              {return new Symbol(sym.CASE, yyline+1, yycolumn+1, yytext());}
(default)              {return new Symbol(sym.DEFAULT, yyline+1, yycolumn+1, yytext());}
// out break points
(break)             {return new Symbol(sym.BREAK, yyline+1, yycolumn+1, yytext());}
(return)            {return new Symbol(sym.RETURN, yyline+1, yycolumn+1, yytext());}
("true")            {return new Symbol(sym.TRUE, yyline+1, yycolumn+1, yytext());}
("false")           {return new Symbol(sym.FALSE, yyline+1, yycolumn+1, yytext());}
// strings
{VAL_COMILL}        {return new Symbol(sym.VAL_COMILLAS, yyline+1, yycolumn+1, yytext());}
{SING_LETT}         {return new Symbol(sym.CHAR, yyline+1, yycolumn+1, yytext());}
// COMMENTS
{LINE_COMM}         {/* ignore */ saveComment(yytext(), false); incCommentsCounter(); } // line comment
{LONG_COMM}         {/* ignore */ saveComment(yytext(), true); incCommentsCounter(); } // multi line commment
// ids
{NUM}               {return new Symbol(sym.NUMBER, yyline+1, yycolumn+1, yytext());}
({NUM}"."{NUM})     {return new Symbol(sym.DECIMAL, yyline+1, yycolumn+1, yytext());}
{VAR_TYPE}          {return new Symbol(sym.VAR_TYPE, yyline+1, yycolumn+1, yytext());}
{FUN_TYPE}          {return new Symbol(sym.FUNC_TYPE, yyline+1, yycolumn+1, yytext());}
{ID}                {return new Symbol(sym.ID, yyline+1,yycolumn+1,yytext());}

// UNKNOWN
[^]                 {/* unknown */ addError(); return new Symbol(sym.UNKNOWN, yyline+1, yycolumn+1, yytext()); }