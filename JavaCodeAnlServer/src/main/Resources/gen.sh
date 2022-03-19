echo "-------------------------------"
echo "GENERATING LEXERS"
echo "------> deleting old lexers"
rm -rf ../java/Backend/Objects/Lexers/JavaCodeLexer.java
echo "------> compiling new lexers"
jflex ./lexer.jflex
echo "------> moving java files"
mv ./JavaCodeLexer.java ../java/Backend/Objects/Lexers/
echo "---> JAVA FILES MOVED"

echo "-------------------------------"
echo "GENERATING PARSERS"
echo "------> deleting old parsers"
rm -rf ../java/Parsers/JavaCodeParser.java ../Parsers/sym.java
echo "------> compiling new parsers"
cup -parser JavaCodeParser -symbols sym Syntax.cup
echo "------> moving java files"
mv ./JavaCodeParser.java ./sym.java ../java/Backend/Objects/Parsers/
echo "---> JAVA FILES MOVED"