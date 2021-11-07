import java.util.Scanner;

public class Console {
    private final FA fa;

    public Console(FA fa) {
        this.fa = fa;
    }

    public void displayMenu() {
        System.out.println();
        System.out.println("1. Read FA from file");
        System.out.println("2. Display FA");
        System.out.println("3. Display states");
        System.out.println("4. Display alphabet");
        System.out.println("5. Display initial state");
        System.out.println("6. Display transitions");
        System.out.println("7. Display final states");
        System.out.println("8. Check if FA is valid");
        System.out.println("9. Check if FA is DFA");
        System.out.println("10. Check is the sequence if accepted");
        System.out.println("11. Exit");
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        while (true) {
            this.displayMenu();
            System.out.println("->");
            String command = in.nextLine();
            if (command.equals("1")) {
                System.out.println("Filename ->");
                String fileName = in.nextLine();
                fa.readFromFile(fileName);
            }
            else if (command.equals("2")) {
                System.out.println(fa);
            }
            else if (command.equals("3")) {
                System.out.println("States: " + fa.getQ());
            }
            else if (command.equals("4")) {
                System.out.println("Alphabet: " + fa.getE());
            }
            else if (command.equals("5")) {
                System.out.println("Initial state: " + fa.getQ0());
            }
            else if (command.equals("6")) {
                System.out.println("Transitions: " + fa.getS());
            }
            else if (command.equals("7")) {
                System.out.println("Final states: " + fa.getF());
            }
            else if (command.equals("8")) {
                if (fa.validate()) {
                    System.out.println("FA is valid!");
                } else {
                    System.out.println("FA is not valid!");
                }
            }
            else if (command.equals("9")) {
                if (fa.isDfa()){
                    System.out.println("FA is a DFA!");
                }
                else{
                    System.out.println("FA is not a DFA!");
                }
            }
            else if (command.equals("10")) {
                System.out.println("Sequence ->");
                String sequence = in.nextLine();
                if (fa.isAccepted(sequence)){
                    System.out.println("Sequence is accepted!");
                }
                else{
                    System.out.println("Sequence is not accepted!");
                }
            }
            else if (command.equals("11")) {
                break;
            }
        }
    }
}
