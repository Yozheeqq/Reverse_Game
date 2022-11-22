public class Game {
    private final static int FIELD_SIZE = 8;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private final User user = new User();
    private final Computer computer = new Computer();
    private int computerPoints = 2;
    private int userPoints = 2;


    private char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    private char[][] prevField = new char[FIELD_SIZE][FIELD_SIZE];

    public void showMenu() {
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
                    InteractMenu.showHowToUseInterface();
                    startGame();
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

    private void startGame() {
        initializeStartParams();
        drawField();
        while(true) {
            user.makeMove();
            if(isGameOver()) {
                break;
            }
            computer.makeMove();
            if(isGameOver()) {
                break;
            }
            // Тут предлагаем отменить ход
            // Нужен метод для этого
        }
        // Тут пишем, кто выиграл
        // Выводим кол-во очков
    }

    private boolean isGameOver() {
        return false;
    }

    private void drawField() {
        System.out.println("---------------");
        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
                if(field[i][j] == 'Y') {
                    System.out.print(ANSI_WHITE + "Y " + ANSI_RESET);
                } else if (field[i][j] == 'X') {
                    System.out.print(ANSI_PURPLE + "X " + ANSI_RESET);
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    private void initializeStartParams() {
        computerPoints = 2;
        userPoints = 2;
        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = '0';
            }
        }
        field[3][3] = 'Y';
        field[4][4] = 'Y';
        field[3][4] = 'X';
        field[4][3] = 'X';
        prevField = field;
    }
}
