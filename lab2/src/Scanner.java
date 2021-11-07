import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

    SymbolTable symbolTable = new SymbolTable(50);
    ProgramInternalForm pif = new ProgramInternalForm();
    LanguageSpecification specification = new LanguageSpecification();
    FA fa = new FA();

    public Scanner() {}

    public void ScanFile(String filename) {
        try {
            File file = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            String line;
            StringBuilder output = new StringBuilder();

            while((line = br.readLine()) != null) {
                List<String> tokenList = TokenizeLine(line);
//                System.out.println(tokenList);
                ScanLine(tokenList, i, output);
                i++;
            }

            WritePifToFile(filename, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WritePifToFile(String filename, StringBuilder output) {
        try {
            filename = "src/files/output/" + filename.substring(9, filename.length() - 4) + "Output.txt";
            FileWriter fw = new FileWriter(filename);
            fw.write(symbolTable.toString());
//            fw.write(pif.toString());
            fw.write(output.toString());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> TokenizeLine(String line) {
        List<String> tokens = new ArrayList<>();
        int i = 0;

        while (i < line.length()) {

            if (line.charAt(i) == '"') {
                // user' declared strings
                StringBuilder stringToken = new StringBuilder("\"");
                i++;
                while (i < line.length() && line.charAt(i) != '"') {
                    stringToken.append(line.charAt(i));
                    i++;
                }
                stringToken.append("\"");
                i++;
                tokens.add(stringToken.toString());
            }
            else if (isSeparator(String.valueOf(line.charAt(i)))) {
                // separators excluding space, tab and new line
                if (!(line.charAt(i) == ' ') && !(line.charAt(i) == '\t') && !(line.charAt(i) == '\n')) {
                    tokens.add(String.valueOf(line.charAt(i)));
                }
                i++;
            }
            else if (isOperator(String.valueOf(line.charAt(i)))) {
                // composed operators and operators
                if(isOperator(String.valueOf(line.charAt(i + 1)))) {
                    tokens.add(line.charAt(i) + String.valueOf(line.charAt(i + 1)));
                    i = i + 2;
                }
                else {
                    tokens.add(String.valueOf(line.charAt(i)));
                    i++;
                }
            }
            else {
                // other tokens like reserved words, constants and identifiers
                StringBuilder token = new StringBuilder(String.valueOf(line.charAt(i)));
                i++;
                while (i < line.length() && !isSeparator(String.valueOf(line.charAt(i))) && !isOperator(String.valueOf(line.charAt(i)))) {
                    token.append(line.charAt(i));
                    i++;
                }
                tokens.add(token.toString());
            }
        }

        return tokens;
    }

    public void ScanLine(List<String> tokens, int line, StringBuilder output) {
        String lastToken = "";

        for(int i = 0; i < tokens.size(); ++i) {
            String token = tokens.get(i);

            if (isConstant(token)){
                int code = specification.getCodification().get("constant"); // 1
                symbolTable.Add(token);
                var position = symbolTable.Search(token);
                pif.Add(code, position);
                output.append("Token " + token + " on position: " + position + "\n");
            }
            else if ((token.equals("-") || token.equals("+")) && (isNumber(tokens.get(i + 1))) &&
                    (lastToken.equals("=") || lastToken.equals("("))) {
                token += tokens.get(i + 1);
                i++;
                if (!token.equals("-0")){
                    int code = specification.getCodification().get("constant"); // 1
                    symbolTable.Add(token);
                    var position = symbolTable.Search(token);
                    pif.Add(code, position);
                    output.append("Token " + token + " on position: " + position + "\n");
                }
                else {
                    output.append("Error at line " + line + ". Invalid token: " + token + "\n");
                }
            }
            else if (isOperator(token) || isSeparator(token) || isReservedWord(token)) {
                int code = specification.getCodification().get(token);
                pif.Add(code, -1);
                output.append("Token " + token + " on position: " + -1 + "\n");
            }
            else if (isIdentifier(token)){
                int code = specification.getCodification().get("identifier"); // 0
                symbolTable.Add(token);
                var position = symbolTable.Search(token);
                pif.Add(code, position);
                output.append("Token " + token + " on position: " + position + "\n");
            }
            else {
                output.append("Error at line " + line + ". Invalid token: " + token + "\n");
            }
            lastToken = token;
        }
    }

    public boolean isSeparator(String token) {
        return this.specification.getSeparators().contains(token);
    }

    public boolean isOperator(String token){
        return this.specification.getOperators().contains(token);
    }

    public boolean isReservedWord(String token){
        return this.specification.getReservedWords().contains(token);
    }

    public boolean isNumber(String token){
        String number = "^([+|-]?[1-9][0-9]*)|0$";
        return token.matches(number);
    }

    public boolean isString(String token){
        String string = "^\"[a-zA-Z0-9_.:;,?!*' ]*\"$";
        return token.matches(string);
    }

    public boolean isCharacter(String token){
        String character = "^\'[a-zA-Z0-9_]\'$";
        return token.matches(character);
    }

    public boolean isConstant(String token){
//        return isNumber(token) || isString(token) || isCharacter(token);
        fa = new FA();
        fa.readFromFile("src/files/numberFA.in");
        return fa.isAccepted(token) || isString(token) || isCharacter(token);
    }

    public boolean isIdentifier(String token){
//        String pattern = "^[a-zA-Z]([a-zA-Z0-9_]*$)";
//        return token.matches(pattern);
        fa = new FA();
        fa.readFromFile("src/files/identifierFA.in");
        return fa.isAccepted(token);
    }
}
