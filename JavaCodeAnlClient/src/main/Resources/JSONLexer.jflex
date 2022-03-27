/**************************************************/
/************** SECTION 1: USER CODE **************/
/**************************************************/

package Backend.Objects.Lexers;
import java_cup.runtime.*;
import Backend.Objects.Parsers.sym;

/**************************************************/
/*************** SECTION 2: CONFIGS ***************/
/**************************************************/
%%
%class JSONLexer
%type java_cup.runtime.Symbol
%cup
%column
%line
%full
%unicode
%public

// define custom
VAL_COMILLAS=("\""[^\"\n\r]*"\"")
WHITESPACES=[\s\t\r\n]+
NUMBER=([0-9]+)

// methods
%{
    // TODO add methodsI
%}

%%


/**************************************************/
/************ SECTION 3: LEXICAL RULES ************/
/**************************************************/

/// ignore
{WHITESPACES}   { /*IGNORE*/ }
// symbols
("{")           { return new Symbol(sym.BRACE_O, yyline+1, yycolumn+1); }
("}")           { return new Symbol(sym.BRACE_C, yyline+1, yycolumn+1); }
("[")           { return new Symbol(sym.BRACKET_O, yyline+1, yycolumn+1); }
("]")           { return new Symbol(sym.BRACKET_C, yyline+1, yycolumn+1); }
(",")           { return new Symbol(sym.COMMA, yyline+1, yycolumn+1); }
(":")           {  return new Symbol(sym.COLON, yyline+1, yycolumn+1); }
// reserved words
(Tipo)          { return new Symbol(sym.TYPE, yyline+1, yycolumn+1); }
(Texto)         { return new Symbol(sym.TEXT, yyline+1, yycolumn+1); }
(Score)         { return new Symbol(sym.SCORE, yyline+1, yycolumn+1); }
(Clases)        { return new Symbol(sym.CLASSES, yyline+1, yycolumn+1); }
(Metodos)       { return new Symbol(sym.METHODS, yyline+1, yycolumn+1); }
(Funcion)       { return new Symbol(sym.FUNCTION, yyline+1, yycolumn+1); }
(Nombre)        { return new Symbol(sym.NAME, yyline+1, yycolumn+1); }
(Variables)     { return new Symbol(sym.VARIABLES, yyline+1, yycolumn+1); }
(Parametros)    { return new Symbol(sym.PARAMETER, yyline+1, yycolumn+1); }
(Comentarios)   { return new Symbol(sym.COMMENTS, yyline+1, yycolumn+1); }
// data
{NUMBER}        { return new Symbol(sym.NUMBER, yyline+1, yycolumn+1, yytext()); }
{VAL_COMILLAS}  { return new Symbol(sym.STRING, yyline+1, yycolumn+1, yytext()); }

[^]             { return new Symbol(sym.UNKNOWN, yyline+1, yycolumn+1, yytext()); }