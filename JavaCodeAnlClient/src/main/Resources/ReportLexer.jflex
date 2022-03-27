/**************************************************/
/************** SECTION 1: USER CODE **************/
/**************************************************/

package Backend.Objects.Lexers;
import java_cup.runtime.*;
import Backend.Objects.Parsers.repSym;

/**************************************************/
/*************** SECTION 2: CONFIGS ***************/
/**************************************************/
%%
%class ReportLexer
%type java_cup.runtime.Symbol
%cup
%column
%line
%full
%unicode
%public

// define custom
VAL_COMILLAS=("\""[^\"\n\r]*"\"")
LETTER=[a-zA-ZáéíóúÁÉÍÓÚñÑ]+
ID=(({LETTER}|"_"|{NUMBER})+)
WHITESPACES=[\s\t\r\n]+
COMMENT=(\<\/([^</]|[\r\n]|(\/([^>]|[\r\n])))*?\/\>)
NUMBER=[0-9]+

// methods
%{
    
%}

%%


/**************************************************/
/************ SECTION 3: LEXICAL RULES ************/
/**************************************************/

/// ignore
{WHITESPACES}   { /*IGNORE*/ }
// symbols
(".")           { return new Symbol(repSym.DOT, yyline+1, yycolumn+1); }
("[")           { return new Symbol(repSym.BRACK_O, yyline+1, yycolumn+1); }
("]")           { return new Symbol(repSym.BRACK_C, yyline+1, yycolumn+1); }
(";")           { return new Symbol(repSym.SEMICOLON, yyline+1, yycolumn+1); }
(":")           { return new Symbol(repSym.COLON, yyline+1, yycolumn+1); }
(">")           { return new Symbol(repSym.GT, yyline+1, yycolumn+1); }
// assign symobls
("=")           { return new Symbol(repSym.EQUALS, yyline+1, yycolumn+1); }
// conj symbols
("$$")           { return new Symbol(repSym.DOLAR, yyline+1, yycolumn+1); }
// arithm symbols
("+")           { return new Symbol(repSym.PLUS, yyline+1, yycolumn+1); }
("-")           { return new Symbol(repSym.LESS, yyline+1, yycolumn+1); }
("*")           { return new Symbol(repSym.MULT, yyline+1, yycolumn+1); }
("/")           { return new Symbol(repSym.DIV, yyline+1, yycolumn+1); }
("(")           { return new Symbol(repSym.PAR_O, yyline+1, yycolumn+1); }
(")")           { return new Symbol(repSym.PAR_C, yyline+1, yycolumn+1); }
// reserved
// reserved var assignation
(Tipo)          { return new Symbol(repSym.TYPE, yyline+1, yycolumn+1); }
(Score)         { return new Symbol(repSym.SCORE, yyline+1, yycolumn+1); }
(Texto)         { return new Symbol(repSym.TEXT, yyline+1, yycolumn+1); }
(Nombre)        { return new Symbol(repSym.NAME, yyline+1, yycolumn+1); }
(Clases)        { return new Symbol(repSym.CLASSES, yyline+1, yycolumn+1); }
(RESULT)        { return new Symbol(repSym.RESULT, yyline+1, yycolumn+1); }
(Metodos)       { return new Symbol(repSym.METHODS, yyline+1, yycolumn+1); }
(Funcion)       { return new Symbol(repSym.FUNCTION, yyline+1, yycolumn+1); }
(Integer)       { return new Symbol(repSym.INTEGER, yyline+1, yycolumn+1); }
(Variables)     { return new Symbol(repSym.VARIABLES, yyline+1, yycolumn+1); }
(Parametros)    { return new Symbol(repSym.PARAMETERS, yyline+1, yycolumn+1); }
(Comentarios)   { return new Symbol(repSym.COMMENTS, yyline+1, yycolumn+1); }
(String)        { return new Symbol(repSym.STRING_TYPE, yyline+1, yycolumn+1); }
(iterador)      { return new Symbol(repSym.ITERATOR, yyline+1, yycolumn+1); }
(hasta)         { return new Symbol(repSym.UNTIL, yyline+1, yycolumn+1); }
// reserved html
(\<html\>)          { return new Symbol(repSym.HTML_O, yyline+1, yycolumn+1); }
(\<\/html\>)        { return new Symbol(repSym.HTML_C, yyline+1, yycolumn+1); }
(\<h1\>)            { return new Symbol(repSym.H1_O, yyline+1, yycolumn+1); }
(\<\/h1\>)          { return new Symbol(repSym.H1_C, yyline+1, yycolumn+1); }
(\<h2\>)            { return new Symbol(repSym.H2_O, yyline+1, yycolumn+1); }
(\<\/h2\>)          { return new Symbol(repSym.H2_C, yyline+1, yycolumn+1); }
(\<table\>)         { return new Symbol(repSym.TABLE_O, yyline+1, yycolumn+1); }
(\<\/table\>)       { return new Symbol(repSym.TABLE_C, yyline+1, yycolumn+1); }
(\<for)             { return new Symbol(repSym.FOR_O, yyline+1, yycolumn+1); }
(\<\/for\>)         { return new Symbol(repSym.FOR_C, yyline+1, yycolumn+1); }
(\<tr\>)            { return new Symbol(repSym.TR_O, yyline+1, yycolumn+1); }
(\<\/tr\>)          { return new Symbol(repSym.TR_C, yyline+1, yycolumn+1); }
(\<th\>)            { return new Symbol(repSym.TH_O, yyline+1, yycolumn+1); }
(\<\/th\>)          { return new Symbol(repSym.TH_C, yyline+1, yycolumn+1); }
(\<td\>)            { return new Symbol(repSym.TD_O, yyline+1, yycolumn+1); }
(\<\/td\>)          { return new Symbol(repSym.TD_C, yyline+1, yycolumn+1); }
(\<br\>)            { return new Symbol(repSym.BR, yyline+1, yycolumn+1); }
// data
{NUMBER}        { return new Symbol(repSym.NUMBER, yyline+1, yycolumn+1, yytext()); }
{VAL_COMILLAS}  { return new Symbol(repSym.STRING, yyline+1, yycolumn+1, yytext()); }
{ID}            { return new Symbol(repSym.ID, yyline+1, yycolumn+1, yytext()); }
{COMMENT}       { /*IGNORE*/ System.out.println("Coment: \n\n" + yytext()); }
// custom EOF
<<EOF>>         { return new Symbol(repSym.EOF, -1, -1); }
// error
[^]             { return new Symbol(repSym.UNKNOWN, yyline+1, yycolumn+1, yytext()); }