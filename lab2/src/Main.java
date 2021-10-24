public class Main {

    static void testSymbolTable() {
        SymbolTable st = new SymbolTable(10);

        System.out.println(st.Add("a"));
        System.out.println(st.Add("b"));
        System.out.println(st.Add("d"));
        System.out.println(st.Add("d"));
        System.out.println(st.Add("l"));
        System.out.println(st.Add("ab"));
        System.out.println(st.Add("ba"));

        System.out.println(st.Search("a"));
        System.out.println(st.Search("b"));
        System.out.println(st.Search("d"));
        System.out.println(st.Search("l"));
        System.out.println(st.Search("ab"));
        System.out.println(st.Search("ba"));
    }

    public static void main(String[] args){
        Scanner s = new Scanner();
        s.ScanFile("src/files/p1.txt");
        Scanner s1 = new Scanner();
        s1.ScanFile("src/files/p2.txt");
        Scanner s2 = new Scanner();
        s2.ScanFile("src/files/p3.txt");
        Scanner s3 = new Scanner();
        s3.ScanFile("src/files/p1err.txt");
    }
}
