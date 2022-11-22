public class Game {
    private final User user = new User();
    private final Computer computer = new Computer();

    private char[][] field = new char[8][8];
    private char[][] prevField = new char[8][8];

    public void startGame() {
        InteractMenu.showMenu();
        String answer;
        boolean isMenu = true;
        do {
            answer = InteractMenu.SCANNER.nextLine();
            switch (answer) {
                case "1" -> {
                    isMenu = false;
                    int level = InteractMenu.chooseLevel();
                    computer.setLevel(level);
                }
                case "2" -> isMenu = false;
                case "3" -> System.out.println("Максимум очков: " + user.getMaxPoints());
                case "4" -> {
                    InteractMenu.showGoodBye();
                    isMenu = false;
                }
                default -> InteractMenu.showIncorrectInput();
            }
        } while (isMenu);
    }
}
