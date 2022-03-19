/* SECTION 1: USER CODE */
package Backend.Objects.Lexers;
import java_cup.runtime.*;
import Backend.Objects.Parsers.sym;
import java.util.ArrayList;

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

    private ArrayList<String> comments = new ArrayList<>();

    public ArrayList<String> getComments () { return this.comments; }

    public void saveComment(String comment) { this.comments.add(comment); }

%}


%%

/* SECTION 3: LEXICAL RULES */

// IGNORE
// whitespaces
{WHITESPACES}       {/* ignore */}

// SYMBOLS
// split
(".")               {return new Symbol(sym.DOT, yyline+1, yycolumn+1);}
(",")               {return new Symbol(sym.COMMA, yyline+1, yycolumn+1);}
//  arithm
("+")               {return new Symbol(sym.PLUS, yyline+1, yycolumn+1);}
("-")               {return new Symbol(sym.MINUS, yyline+1, yycolumn+1);}
("/")               {return new Symbol(sym.DIV, yyline+1, yycolumn+1);}
("%")               {return new Symbol(sym.MOD, yyline+1, yycolumn+1);}
("*")               {return new Symbol(sym.STAR, yyline+1, yycolumn+1);}
//  relational
(">=")              {return new Symbol(sym.GE, yyline+1, yycolumn+1);}
(">")               {return new Symbol(sym.GT, yyline+1, yycolumn+1);}
("<=")              {return new Symbol(sym.LE, yyline+1, yycolumn+1);}
("<")               {return new Symbol(sym.LT, yyline+1, yycolumn+1);}
// assign
("=")               {return new Symbol(sym.ASSIGN, yyline+1, yycolumn+1);}
("==")              {return new Symbol(sym.EQ, yyline+1, yycolumn+1);}
("!=")              {return new Symbol(sym.NE, yyline+1, yycolumn+1);}
// increase and decrease
("++")              {return new Symbol(sym.INC, yyline+1, yycolumn+1);}
("--")              {return new Symbol(sym.DEC, yyline+1, yycolumn+1);}
("*=")              {return new Symbol(sym.MULT_T, yyline+1, yycolumn+1);}
("/=")              {return new Symbol(sym.DIV_T, yyline+1, yycolumn+1);}
("+=")              {return new Symbol(sym.PLUS_T, yyline+1, yycolumn+1);}
("-=")              {return new Symbol(sym.LESS_T, yyline+1, yycolumn+1);}
("!")               {return new Symbol(sym.NOT, yyline+1, yycolumn+1);}
//  logical
("&&")              {return new Symbol(sym.AND, yyline+1, yycolumn+1);}
("||")              {return new Symbol(sym.OR, yyline+1, yycolumn+1);}
// group
("{")               {return new Symbol(sym.BRAC_OPEN, yyline+1, yycolumn+1);}
("}")               {return new Symbol(sym.BRAC_CLOSE, yyline+1, yycolumn+1);}
("(")               {return new Symbol(sym.PAR_OPEN, yyline+1, yycolumn+1);}
(")")               {return new Symbol(sym.PAR_CLOSE, yyline+1, yycolumn+1);}
// end 
(";")               {return new Symbol(sym.SEMICOLON, yyline+1, yycolumn+1);}
(":")               {return new Symbol(sym.COLON, yyline+1, yycolumn+1);}

// METHODS
// import
(import)            {return new Symbol(sym.IMPORT, yyline+1, yycolumn+1);}
// accessibility type
{VISIB}             {return new Symbol(sym.VISIBILITY, yyline+1, yycolumn+1, yytext());}
// class
(class)             {return new Symbol(sym.CLASS, yyline+1, yycolumn+1);}
// functions
(if)                {return new Symbol(sym.IF, yyline+1, yycolumn+1);}
(else)              {return new Symbol(sym.ELSE, yyline+1, yycolumn+1);}
(for)               {return new Symbol(sym.FOR, yyline+1, yycolumn+1);}
(while)             {return new Symbol(sym.WHILE, yyline+1, yycolumn+1);}
(do)                {return new Symbol(sym.DO, yyline+1, yycolumn+1);}
(switch)            {return new Symbol(sym.SWITCH, yyline+1, yycolumn+1);}
(package)           {return new Symbol(sym.PACKAGE, yyline+1, yycolumn+1);}
// other method access
(static)            {return new Symbol(sym.STATIC, yyline+1, yycolumn+1);}
(final)             {return new Symbol(sym.FINAL, yyline+1, yycolumn+1);}
(new)               {return new Symbol(sym.NEW, yyline+1, yycolumn+1);}
(case)              {return new Symbol(sym.CASE, yyline+1, yycolumn+1);}
(default)              {return new Symbol(sym.DEFAULT, yyline+1, yycolumn+1);}
// out break points
(break)             {return new Symbol(sym.BREAK, yyline+1, yycolumn+1);}
(return)            {return new Symbol(sym.RETURN, yyline+1, yycolumn+1);}
("true")            {return new Symbol(sym.TRUE, yyline+1, yycolumn+1);}
("false")           {return new Symbol(sym.FALSE, yyline+1, yycolumn+1);}
// strings
{VAL_COMILL}        {return new Symbol(sym.VAL_COMILLAS, yyline+1, yycolumn+1, yytext());}
{SING_LETT}         {return new Symbol(sym.CHAR, yyline+1, yycolumn+1, yytext());}
// COMMENTS
{LINE_COMM}         {/* ignore */ saveComment(yytext()); } // line comment
{LONG_COMM}         {/* ignore */ saveComment(yytext()); } // multi line commment
// ids
{NUM}               {return new Symbol(sym.NUMBER, yyline+1, yycolumn+1, yytext());}
({NUM}"."{NUM})     {return new Symbol(sym.DECIMAL, yyline+1, yycolumn+1, yytext());}
{VAR_TYPE}          {return new Symbol(sym.VAR_TYPE, yyline+1, yycolumn+1, yytext());}
{FUN_TYPE}          {return new Symbol(sym.FUNC_TYPE, yyline+1, yycolumn+1, yytext());}
{ID}                {return new Symbol(sym.ID, yyline+1,yycolumn+1,yytext());}

// UNKNOWN
[^]                 {/* unknown */ return new Symbol(sym.UNKNOWN, yyline+1, yycolumn+1, yytext()); }