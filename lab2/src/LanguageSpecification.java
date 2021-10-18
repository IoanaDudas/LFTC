import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanguageSpecification {

    private final String[] separators = {"[", "]", "(", ")", ";", " ", ":", "\n", "\t"};
    private final String[] operators = {"+", "-", "*", "/", "%", "=", "<", "<=", "==", ">=", ">", "!=", "&&", "||", "!", "++", "--"};
    private final String[] reservedWords = {"int", "string", "char", "bool", "return", "while", "do",
            "for", "if", "else", "read", "write", "struct", "or", "and"};
    private final HashMap<String, Integer> codification;

    public LanguageSpecification () {
        codification = createCodification();
    }

    private HashMap<String, Integer> createCodification(){
        HashMap<String, Integer> codification = new HashMap<>();

        codification.put("identifier", 0);
        codification.put("constant", 1);

        int code = 2;

        for (String separator: separators) {
            codification.put(separator, code);
            code++;
        }

        for (String operator: operators) {
            codification.put(operator, code);
            code++;
        }

        for (String reservedWord: reservedWords) {
            codification.put(reservedWord, code);
            code++;
        }

        return codification;
    }

    public HashMap<String, Integer> getCodification() {
        return codification;
    }

    public List<String> getOperators() {
        return Arrays.asList(operators);
    }

    public List<String> getReservedWords() {
        return Arrays.asList(reservedWords);
    }

    public List<String> getSeparators() {
        return Arrays.asList(separators);
    }

    @Override
    public String toString() {
        return codification.toString();
    }
}
