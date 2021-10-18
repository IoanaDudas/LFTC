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
}
