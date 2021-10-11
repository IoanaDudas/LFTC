import java.util.Arrays;

public class SymbolTable {
    private final int capacity;
    private final String[] codification;


    public SymbolTable (int capacity) {
        this.capacity = capacity;
        this.codification = new String[capacity];
    }

    private int HashFunction (String key){
        int asciiSum = 0;
        for (int i = 0; i < key.length(); i++){
            asciiSum += key.charAt(i);
        }
        return asciiSum % capacity;
    }

    public int Search (String key){
        int hashValue = HashFunction(key);
        while (codification[hashValue] != null){
            if (codification[hashValue].equalsIgnoreCase(key)){
                return hashValue;
            }
            hashValue++;
        }
        return -1;
    }

    public boolean Add (String key){
        for (String entry : codification){
            if (entry != null && entry.equals(key))
                return false;
        }

        int hashValue = HashFunction(key);
        if (codification[hashValue] == null){
            codification[hashValue] = key;
            return true;
        }

        int newHashValue = hashValue;
        while (codification[newHashValue] != null){
            newHashValue++;
        }
        if (codification[newHashValue] == null){
            codification[newHashValue] = key;
            return true;
        }

        return false;
    }

    public String toString (){
        return Arrays.toString(codification);
    }
}
