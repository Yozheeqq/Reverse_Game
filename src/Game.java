import java.util.List;

public class Game {
    public final static int FIELD_SIZE = 8;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private final User user = new User();
    private final Computer computer = new Computer();
    private int computerPoints = 2;
    private int userPoints = 2;
    private int emptyFields = 60;
    private boolean userCanMove = true;
    private boolean computerCanMove = true;
    private String winner;

    // X - user
    // Y - computer
    public static char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    private final char[][] prevField = new char[FIELD_SIZE][FIELD_SIZE];

    public int showMenu() {
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
                    return 1;
                }
                default -> InteractMenu.showIncorrectInput();
            }
        } while (isMenu);
        return 0;
    }

    private void startGame() {
        initializeStartParams();
        drawField("Начало");
        while (true) {
            userMove();
            if (isGameOver()) {
                break;
            }
            computerMove();
            if (isGameOver()) {
                break;
            }
            cancelMoveSuggestion();
            copyPrevField();
        }
        drawField("КОНЕЦ");
        user.setMaxPoints(userPoints);
        if ("user".equals(winner)) {
            InteractMenu.showWinner("игрок");
        } else if ("computer".equals(winner)) {
            InteractMenu.showWinner("компьютер");
        }
        InteractMenu.showPoint(userPoints, computerPoints);
    }

    private void userMove() {
        var possiblePositions = Movement.canMove("user");
        userCanMove = possiblePositions.size() != 0;
        if (!userCanMove) {
            InteractMenu.showUserCantMove();
            return;
        }

        drawFieldWithPossibleMoves(possiblePositions);
        var coordsUser = user.makeMove();
        changeChipColor('X', coordsUser[0], coordsUser[1]);
        countChips();
        drawField("Игрок");
    }

    private void computerMove() {
        computerCanMove = Movement.canMove("computer").size() != 0;
        if (!computerCanMove) {
            InteractMenu.showComputerCantMove();
            return;
        }

        var coordsComputer = computer.makeMove();
        changeChipColor('Y', coordsComputer[0], coordsComputer[1]);
        countChips();
        drawField("Компьютер");
    }

    private void showAllPossiblePositions(List<Integer[]> positions) {
        System.out.println("Возможные ходы: ");
        System.out.print("{");
        for (var position : positions) {
            System.out.print("[" + (position[0] + 1) + " " + (position[1] + 1) + "]" + ";");
        }
        System.out.print("}\n");
    }

    private void cancelMoveSuggestion() {
        InteractMenu.cancelMove();
        String answer = InteractMenu.SCANNER.nextLine();
        if ("/cancel".equals(answer)) {
            cancelMove();
        }

    }

    private boolean isGameOver() {
        if (userPoints == 0) {
            winner = "computer";
            return true;
        }
        if (computerPoints == 0) {
            winner = "user";
            return true;
        }
        if (emptyFields == 0 || (!userCanMove && !computerCanMove)) {
            if (userPoints == computerPoints) {
                winner = "draw";
                return true;
            }
            winner = userPoints > computerPoints ? "user" : "computer";
            return true;
        }
        return false;
    }

    private void copyPrevField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.arraycopy(field[i], 0, prevField[i], 0, FIELD_SIZE);
        }
    }

    private void countChips() {
        userPoints = 0;
        computerPoints = 0;
        emptyFields = 0;
        for (var row : field) {
            for (var chip : row) {
                if (chip == 'X') {
                    userPoints++;
                } else if (chip == 'Y') {
                    computerPoints++;
                } else if (chip == '0') {
                    emptyFields++;
                }
            }
        }
    }

    private void cancelMove() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.arraycopy(prevField[i], 0, field[i], 0, FIELD_SIZE);
        }
        drawField("Игрок");
    }

    private void drawField(String player) {
        System.out.println("---------------");
        System.out.println("Сейчас ход " + player);
        System.out.println("Компьютер: " + computerPoints + "; Игрок: " + userPoints);
        System.out.println("---------------");
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[j][7 - i] == 'Y') {
                    System.out.print(ANSI_WHITE + "Y " + ANSI_RESET);
                } else if (field[j][7 - i] == 'X') {
                    System.out.print(ANSI_PURPLE + "X " + ANSI_RESET);
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    private void drawFieldWithPossibleMoves(List<Integer[]> positions) {
        System.out.println("---------------");
        showAllPossiblePositions(positions);
        System.out.println("---------------");
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[j][7 - i] == 'Y') {
                    System.out.print(ANSI_WHITE + "Y " + ANSI_RESET);
                } else if (field[j][7 - i] == 'X') {
                    System.out.print(ANSI_PURPLE + "X " + ANSI_RESET);
                } else if (isPossiblePosition(positions, j, 7 - i)) {
                    System.out.print(ANSI_GREEN + "X " + ANSI_RESET);
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    private boolean isPossiblePosition(List<Integer[]> positions, int x, int y) {
        for (var position : positions) {
            if (position[0] == x && position[1] == y) {
                return true;
            }
        }
        return false;
    }

    private void initializeStartParams() {
        computerPoints = 2;
        userPoints = 2;
        emptyFields = 60;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
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
        field[x][y] = currentChip;
        changeChipColorHorizontal(currentChip, x, y);
        changeChipColorVertical(currentChip, x, y);
        changeChipColorDiagonal(currentChip, x, y);
    }

    private void changeChipColorDiagonal(char currentChip, int x, int y) {
        // TODO Вверх-влево косяк
        // Вроде работает, я хз честно говоря
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k < x && l < y; k++, l++) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for (int i = x + 1, j = y + 1; i < FIELD_SIZE && j < FIELD_SIZE; i++, j++) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k >= x && l >= y; k--, l--) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for (int i = x - 1, j = y + 1; i >= 0 && j < FIELD_SIZE; i--, j++) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k < x && l >= y; k++, l--) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for (int i = x + 1, j = y - 1; i < FIELD_SIZE && j >= 0; i++, j--) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k >= x && l < y; k--, l++) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
    }

    private void changeChipColorHorizontal(char currentChip, int x, int y) {
        for (int i = x - 1; i >= 0; i--) {
            if (field[i][y] == '0') {
                break;
            }
            if (field[i][y] == currentChip) {
                for (int j = i; j < x; j++) {
                    field[j][y] = currentChip;
                }
                break;
            }
        }
        for (int i = x + 1; i < Game.FIELD_SIZE; i++) {
            if (field[i][y] == '0') {
                break;
            }
            if (field[i][y] == currentChip) {
                for (int j = x; j <= i; j++) {
                    field[j][y] = currentChip;
                }
                break;
            }
        }
    }

    private void changeChipColorVertical(char currentChip, int x, int y) {

        for (int i = y - 1; i >= 0; i--) {
            if (field[x][i] == '0') {
                break;
            }
            if (field[x][i] == currentChip) {
                for (int j = i; j < y; j++) {
                    field[x][j] = currentChip;
                }
                break;
            }
        }
        for (int i = y + 1; i < Game.FIELD_SIZE; i++) {
            if (field[x][i] == '0') {
                break;
            }
            if (field[x][i] == currentChip) {
                for (int j = y; j <= i; j++) {
                    field[x][j] = currentChip;
                }
                break;
            }
        }
    }
}
