import java.util.HashMap;

public class ProgramInternalForm {
    private final HashMap<Integer, Integer> content = new HashMap<>();

    public ProgramInternalForm () {}

    public void Add(int code, int position){
        content.put(code, position);
    }

    public HashMap<Integer, Integer> getContent() {
        return content;
    }

    @Override
    public String toString() {
        StringBuilder pif = new StringBuilder("\nPif\nCode\tPos in St\n");
        for (var value : content.entrySet()) {
            pif.append(value.getKey());
            pif.append('\t');
            pif.append(value.getValue());
            pif.append('\n');
        }
        return pif.toString();
    }
}
