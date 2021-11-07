import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class FA {
    private final List<String> Q, E, F;
    private String q0;
    private Map<AbstractMap.SimpleEntry<String, String>, List<String>> S;

    public FA() {
        this.Q = new ArrayList<>();
        this.E = new ArrayList<>();
        this.F = new ArrayList<>();
        this.q0 = "";
        this.S = new HashMap<>();
    }

    public FA(List<String> Q, List<String> E, String q0, List<String> F, Map<AbstractMap.SimpleEntry<String, String>, List<String>> S) {
        this.Q = Q;
        this.E = E;
        this.q0 = q0;
        this.F = F;
        this.S = S;
    }

    public FA(String filename) {
        this.Q = new ArrayList<>();
        this.E = new ArrayList<>();
        this.F = new ArrayList<>();
        this.S = new HashMap<>();
        readFromFile(filename);
    }

    public List<String> getQ() {
        return Q;
    }

    public List<String> getE() {
        return E;
    }

    public String getQ0() {
        return q0;
    }

    public List<String> getF() {
        return F;
    }

    public Map<AbstractMap.SimpleEntry<String, String>, List<String>> getS() {
        return S;
    }

    public boolean validate() {
        if (!Q.contains(q0)) {
            return false;
        }
        for (var state : F) {
            if (!Q.contains(state)) {
                return false;
            }
        }
        for (var key: S.keySet()){
            if (!Q.contains(key.getKey())) {
                return false;
            }
            if (!E.contains(key.getValue())) {
                return false;
            }
            for (var destination : S.get(key)) {
                if (!Q.contains(destination)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void readFromFile(String filename) {
        try{
            File file = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();
            if (line != null){
                Q.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            if (line != null){
                E.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            if (line != null){
                q0 = line.strip();
            }
            line = br.readLine();
            if (line != null){
                F.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            while (line != null){
                String[] tokens = line.split(" ");
                AbstractMap.SimpleEntry<String, String> key = new AbstractMap.SimpleEntry<>(tokens[0], tokens[1]);
                if(S.containsKey(key)){
                    S.get(key).add(tokens[2]);
                }
                else{
                    ArrayList<String> value = new ArrayList<>(Collections.singletonList(tokens[2]));
                    S.put(key, value);
                }
                line = br.readLine();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isDfa() {
        for (var key : S.keySet()) {
            if (S.get(key).size() > 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isAccepted(String sequence) {
        if(isDfa()){
            var currentState = q0;
            for(char symbol : sequence.toCharArray()){
                var key = new AbstractMap.SimpleEntry<>(currentState, String.valueOf(symbol));
                if(S.containsKey(key)){
                    currentState = S.get(key).get(0);
                }
                else {
                    return false;
                }
            }
            return F.contains(currentState);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Q = { " + String.join(", ", Q) + " }\n" +
                "E = { " + String.join(", ", E) + " }\n" +
                "q0 = { " + q0 + " }\n" +
                "F = { " + String.join(", ", F) + " }\n" +
                "S = " + S;
    }
}
