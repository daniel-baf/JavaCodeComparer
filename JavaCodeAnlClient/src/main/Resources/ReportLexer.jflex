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
%full
%unicode
%line
%public

// define custom
VAL_COMILLAS=("\""[^\"\n\r]*"\"")
LETTER=[a-zA-ZáéíóúÁÉÍÓÚñÑ]+
ID=({LETTER}|(({LETTER}|"_")({LETTER}|"_"|{NUMBER})+))
WHITESPACES=[\s\t\r\n]+
COMMENT=(\<\/([^</]|[\r\n]|(\/([^>]|[\r\n])))*?\/\>)
NUMBER=[0-9]+

// methods
%{
      public Symbol calcSym(String text, boolean isClose) {
        try {
          // entry -> <type> or </type> if is close
          String transaction = isClose ? text.substring(2, text.length() - 1) : text.substring(1, text.length() - 1);
          if (text.equalsIgnoreCase("<for") || text.equalsIgnoreCase("</for>")) {
            transaction = "for";
          }
          // check for
          int symT;
          switch (transaction.toLowerCase()) {
            case "html":
              symT = isClose ? repSym.HTML_C : repSym.HTML_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "h1":
              symT = isClose ? repSym.H1_C : repSym.H1_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "h2":
              symT = isClose ? repSym.H2_C : repSym.H2_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "table":
              symT = isClose ? repSym.TABLE_C : repSym.TABLE_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "for":
              symT = isClose ? repSym.FOR_C : repSym.FOR_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "tr":
              symT = isClose ? repSym.TR_C : repSym.TR_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "td":
              symT = isClose ? repSym.TD_C : repSym.TD_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "th":
              symT = isClose ? repSym.TH_C : repSym.TH_O;
              return new Symbol(symT, yyline + 1, yycolumn + 1);
            case "br":
              return new Symbol(repSym.BR, yyline + 1, yycolumn + 1);
            default:
              return new Symbol(repSym.UNKNOWN, yyline+1, yycolumn+1, text);
          }
        } catch (Exception e) {
          System.out.println("Error: " + e.getMessage() + " " + text);
          return new Symbol(repSym.UNKNOWN, yyline+1, yycolumn+1, text);
        }
      }

      public Symbol calcReserved(String text) {
          switch(text.toLowerCase()) {
            case "tipo":
              return new Symbol(repSym.TYPE, yyline+1, yycolumn+1);
            case "score":
              return new Symbol(repSym.SCORE, yyline+1, yycolumn+1);
            case "texto":
              return new Symbol(repSym.TEXT, yyline+1, yycolumn+1);
            case "nombre":
              return new Symbol(repSym.NAME, yyline+1, yycolumn+1);
            case "clases":
              return new Symbol(repSym.CLASSES, yyline+1, yycolumn+1);
            case "result":
              return new Symbol(repSym.RESULT_T, yyline+1, yycolumn+1);
            case "metodos":
              return new Symbol(repSym.METHODS, yyline+1, yycolumn+1);
            case "funcion":
              return new Symbol(repSym.FUNCTION, yyline+1, yycolumn+1);
            case "integer":
              return new Symbol(repSym.INTEGER, yyline+1, yycolumn+1);
            case "variables":
              return new Symbol(repSym.VARIABLES, yyline+1, yycolumn+1);
            case "parametros":
              return new Symbol(repSym.PARAMETERS, yyline+1, yycolumn+1);
            case "comentarios":
              return new Symbol(repSym.COMMENTS, yyline+1, yycolumn+1);
            case "string":
              return new Symbol(repSym.STRING_TYPE, yyline+1, yycolumn+1);
            case "iterador":
              return new Symbol(repSym.ITERATOR, yyline+1, yycolumn+1);
            case "hasta":
              return new Symbol(repSym.UNTIL, yyline+1, yycolumn+1);
            default:
              return new Symbol(repSym.ID, yyline+1, yycolumn+1, text);
          }
      }
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

{NUMBER}        { return new Symbol(repSym.NUMBER, yyline+1, yycolumn+1, yytext()); }
{ID}            { return calcReserved(yytext()); }  
// reserved html
(\<{ID})        { return calcSym(yytext(), false); }
(\<{ID}\>)      { return calcSym(yytext(), false); }
(\<\/{ID}\>)    { return calcSym(yytext(), true); }
// data
{COMMENT}       { /*IGNORE*/ }
{VAL_COMILLAS}  { return new Symbol(repSym.STRING, yyline+1, yycolumn+1, yytext()); }
// custom EOF
<<EOF>>         { return new Symbol(repSym.EOF, -1, -1); }
// error
[^]             { return new Symbol(repSym.UNKNOWN, yyline+1, yycolumn+1, yytext()); }