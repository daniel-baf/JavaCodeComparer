/* SECTION 1: USER CODE */
package Lexer;
import Lexer.Token.Tokens;

/* SECTION 2: CONFIG */
%%
%class JavaCodeLexerTkn
%type Tokens
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
LONG_COMM=(\/\*[^*]*\*+(\?\:[^\/\*][^\*]*\*+)*\/)
VAL_COMILL=("\""[^\"\n\r]*"\"")
SING_LETT=('[a-zA-ZáéíóúÁÉÍÓÚñÑ]')
ID=({LETT}({LETT}|{NUM}|"_")*)

%{
    public String lexeme;
    public int line;
    public int col;

    private void saveData() {
        lexeme = this.yytext();
        line = yyline + 1;
        col = yycolumn + 1;
    }

%}


%%

/* SECTION 3: LEXICAL RULES */

// IGNORE
// whitespaces
{WHITESPACES}           {/* ignore */}

// SYMBOLS
// split
(".")                   {saveData(); return Tokens.DOT;}
(",")                   {saveData(); return Tokens.COMMA;}
//  arithm
("+")                   {saveData(); return Tokens.PLUS;}
("-")                   {saveData(); return Tokens.MINUS;}
("/")                   {saveData(); return Tokens.DIV;}
("%")                   {saveData(); return Tokens.MOD;}
("*")                   {saveData(); return Tokens.STAR;}
//  relational
(">=")                  {saveData(); return Tokens.GE;}
(">")                   {saveData(); return Tokens.GT;}
("<=")                  {saveData(); return Tokens.LE;}
("<")                   {saveData(); return Tokens.LT;}
// assign
("=")                   {saveData(); return Tokens.ASSIGN;}
("==")                  {saveData(); return Tokens.EQ;}
("!=")                  {saveData(); return Tokens.NE;}
// increase and decrease
("++")                  {saveData(); return Tokens.INC;}
("--")                  {saveData(); return Tokens.DEC;}
//  logical
("&&")                  {saveData(); return Tokens.AND;}
("||")                  {saveData(); return Tokens.OR;}
// group
("{")                   {saveData(); return Tokens.BRAC_OPEN;}
("}")                   {saveData(); return Tokens.BRAC_CLOSE;}
("(")                   {saveData(); return Tokens.PAR_OPEN;}
(")")                   {saveData(); return Tokens.PAR_CLOSE;}
// end 
(";")                   {saveData(); return Tokens.SEMICOLON;}
(":")                   {saveData(); return Tokens.COLON;}

// METHODS
// import
(import)                {saveData(); return Tokens.IMPORT;}
// accessibility type
{VISIB}                 {saveData(); return Tokens.VISIBILITY;}
// class
(class)                 {saveData(); return Tokens.CLASS;}
// functions
(if)                    {saveData(); return Tokens.IF;}
(else)                  {saveData(); return Tokens.ELSE;}
(for)                   {saveData(); return Tokens.FOR;}
(while)                 {saveData(); return Tokens.WHILE;}
(do)                    {saveData(); return Tokens.DO;}
(switch)                {saveData(); return Tokens.SWITCH;}
(package)               {saveData(); return Tokens.PACKAGE;}
// other method access
(static)                {saveData(); return Tokens.STATIC;}
(final)                 {saveData(); return Tokens.FINAL;}
(new)                   {saveData(); return Tokens.NEW;}
(case)                  {saveData(); return Tokens.CASE;}
(default)               {saveData(); return Tokens.DEFAULT;}
// out break points
(break)                 {saveData(); return Tokens.BREAK;}
(return)                {saveData(); return Tokens.RETURN;}
("true")                {saveData(); return Tokens.TRUE;}
("false")               {saveData(); return Tokens.FALSE;}
// strings
{VAL_COMILL}            {saveData(); return Tokens.VAL_COMILLAS;}
{SING_LETT}             {saveData(); return Tokens.CHAR;}
// COMMENTS
{LINE_COMM}             {/* ignore */} // line comment
{LONG_COMM}             {/* ignore */} // multi line commment
// ids
{NUM}                   {saveData(); return Tokens.NUMBER;}
({NUM}"."{NUM})         {saveData(); return Tokens.DECIMAL;}
{VAR_TYPE}              {saveData(); return Tokens.VAR_TYPE;}
{FUN_TYPE}              {saveData(); return Tokens.FUNC_TYPE;}
{ID}                    {saveData(); return Tokens.ID;}

// UNKNOWN
[^]                     {/* unknown */ saveData(); return Tokens.UNKNOWN; }
