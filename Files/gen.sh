echo "-------------------------------"
echo "GENERATING LEXERS"
echo "------> deleting old lexers"
rm -rf ../java/Lexer/JavaCodeLexerTkn.java
rm -rf ../java/Lexer/JavaCodeLexer.java
echo "------> compiling new lexers"
jflex ./lexerTkn.jflex
jflex ./lexer.jflex
echo "------> moving java files"
mv ./JavaCodeLexerTkn.java ../java/Lexer/
mv ./JavaCodeLexer.java ../java/Lexer/

echo "-------------------------------"
echo "GENERATING PARSERS"
echo "------> deleting old parsers"
rm -rf ../java/Parser/JavaCodeParser.java ../Parser/sym.java
echo "------> compiling new parsers"
cup -parser JavaCodeParser -symbols sym Syntax.cup
echo "------> moving java files"
