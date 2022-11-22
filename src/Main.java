import java.util.Scanner;

public class Main {
    private static final Game GAME = new Game();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        GAME.showMenu();
        String answer;
        boolean isMenu = true;
        do {
            answer = SCANNER.nextLine();
            switch (answer) {
                case "1" -> isMenu = false;
                case "2" -> isMenu = false;
                case "3" -> isMenu = false;
                case "4" -> {
                    GAME.showGoodBye();
                    isMenu = false;
                }
                default -> GAME.showIncorrectInput();
            }
        } while (isMenu);
    }
}