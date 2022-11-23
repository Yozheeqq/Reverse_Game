import java.util.Scanner;

public class Game {
    public final static int FIELD_SIZE = 8;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private final User user = new User();
    private final Computer computer = new Computer();
    private int computerPoints = 2;
    private int userPoints = 2;

    // X - user
    // Y - computer
    public static char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
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
        while(true) {
            drawField("Игрок");
            var coords = user.makeMove();
            changeChipColor('X', coords[0], coords[1]);
            if(isGameOver()) {
                break;
            }
            drawField("Компьютер");
            // Логика для компа - сделать
            computer.makeMove();
            if(isGameOver()) {
                break;
            }
            InteractMenu.cancelMove();
            String answer = InteractMenu.SCANNER.nextLine();
            if("/cancel".equals(answer)) {
                cancelMove();
            }
            copyPrevField();
        }
        // Тут пишем, кто выиграл
        // Выводим кол-во очков
    }

    // Сделать проверку конца игры
    private boolean isGameOver() {
        return false;
    }

    private void copyPrevField() {
        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
                prevField[i][j] = field[i][j];
            }
        }
    }

    private void cancelMove() {
        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = prevField[i][j];
            }
        }
    }

    private void drawField(String player) {
        System.out.println("---------------");
        System.out.println("Сейчас ход " + player);
        System.out.println("---------------");
        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
                if(field[j][7-i] == 'Y') {
                    System.out.print(ANSI_WHITE + "Y " + ANSI_RESET);
                } else if (field[j][7-i] == 'X') {
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
        copyPrevField();
    }

    public void changeChipColor(char currentChip, int x, int y) {
        changeChipColorHorizontal(currentChip, x, y);
        changeChipColorVertical(currentChip, x, y);
        changeChipColorDiagonal(currentChip, x, y);
    }

    private void changeChipColorDiagonal(char currentChip, int x, int y) {
        for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if(field[i][j] == currentChip) {
                for(int k = i, l = j; k < x && l < y; k++, l++) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for(int i = x + 1, j = y + 1; i < FIELD_SIZE && j < FIELD_SIZE; i++, j++) {
            if(field[i][j] == currentChip) {
                for(int k = i, l = j; k >= x && l >= y; k--, l--) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for(int i = x - 1, j = y + 1; i >= 0 && j < FIELD_SIZE; i--, j++) {
            if(field[i][j] == currentChip) {
                for(int k = i, l = j; k < x && l >= y; k++, l--) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for(int i = x + 1, j = y - 1; i < FIELD_SIZE && j >= 0; i++, j--) {
            if(field[i][j] == currentChip) {
                for(int k = i, l = j; k >= x && l < y; k--, l++) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
    }

    private void changeChipColorHorizontal(char currentChip, int x, int y) {
        for(int i = x - 1; i >= 0; i--) {
            if(Game.field[i][y] == currentChip) {
                for(int j = i; j < x; j++) {
                    Game.field[j][y] = currentChip;
                }
                break;
            }
        }
        for(int i = x + 1; i < Game.FIELD_SIZE; i++) {
            if(Game.field[i][y] == currentChip) {
                for(int j = x; j <= i; j++) {
                    Game.field[j][y] = currentChip;
                }
                break;
            }
        }
    }

    private void changeChipColorVertical(char currentChip, int x, int y) {
        for(int i = y - 1; i >= 0; i--) {
            if(Game.field[x][i] == currentChip) {
                for(int j = i; j < y; j++) {
                    Game.field[x][j] = currentChip;
                }
                break;
            }
        }
        for(int i = y + 1; i < Game.FIELD_SIZE; i++) {
            if(Game.field[x][i] == currentChip) {
                for(int j = y; j <= i; j++) {
                    Game.field[x][j] = currentChip;
                }
                break;
            }
        }
    }
}
