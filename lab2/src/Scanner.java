import java.util.regex.Pattern;

public class Scanner {

    SymbolTable symbolTable = new SymbolTable(100);
    ProgramInternalForm pif = new ProgramInternalForm();
    LanguageSpecification specification = new LanguageSpecification();

    public boolean isSeparator(String ch){
        return specification.getSeparators().contains(ch);
    }

    public boolean isOperator(String ch){
        return specification.getOperators().contains(ch);
    }

    public boolean isReservedWord(String ch){
        return specification.getReservedWords().contains(ch);
    }

    public boolean isConstant(String ch){
        return (isCharacter(ch) || isInteger(ch));
    }

    public boolean isCharacter(String ch){
        Pattern pattern = Pattern.compile("^'[a-zA-Z0-9]'$");
        return (pattern.matcher(ch).matches());
    }

    public boolean isInteger(String ch){
        Pattern pattern = Pattern.compile("[-]?\\d+");
        return (pattern.matcher(ch).matches());
    }

}
