echo "-------------------------------"
echo "GENERATING LEXERS"
echo "------> deleting old lexers"
rm -rf ../java/Backend/Objects/Lexers/JSONLexer.java ../java/Backend/Objects/Lexers/ReportLexer.java
echo "------> compiling new lexers"
jflex ./JSONLexer.jflex
jflex ./ReportLexer.jflex
echo "------> moving java files"
mv ./JSONLexer.java ./ReportLexer.java ../java/Backend/Objects/Lexers/

echo "---> JAVA FILES MOVED"

echo "-------------------------------"
echo "GENERATING PARSERS"
echo "------> deleting old parsers"
rm -rf ../java/Parsers/JSONParser.java ../Parsers/sym.java ../Parsers/ReportParser.java ../Parsers/repSym.java
echo "------> compiling new parsers"
cup -parser JSONParser -symbols sym JSONSyntax.cup
cup -parser ReportParser -symbols repSym ReportSyntax.cup
echo "------> moving java files"
mv ./JSONParser.java ./sym.java ./ReportParser.java ./repSym.java ../java/Backend/Objects/Parsers/
echo "---> JAVA FILES MOVED"