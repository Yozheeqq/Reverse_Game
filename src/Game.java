import java.util.List;

/**
 * Класс для взаимодействия с игрой
 */
public class Game {
    // Цвета консоли
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    public final static int FIELD_SIZE = 8;
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

    /**
     * Показывает меню в консоли.
     * @return Состояние конца игры. 1 - выход из игры
     */
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
                case "2" -> {
                    isMenu = false;
                    dualGameStart();
                }
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

    /**
     * Начало игры вдвоем
     */
    private void dualGameStart() {
        initializeStartParams();
        drawField("Начало");
        while(true) {
            userMove("user");
            if (isDualGameOver()) {
                break;
            }
            cancelMoveSuggestion();
            copyPrevField();
            userMove("secondUser");
            if (isDualGameOver()) {
                break;
            }
            cancelMoveSuggestion();
            copyPrevField();
        }
        drawField("КОНЕЦ");
        var points = countDualPoints();
        System.out.println("X: " + points[0] + "; Y: " + points[1]);
    }

    /**
     * Проверка, что игра вдвоем не закончилась
     * @return true - не закончилась, false - закончилась
     */
    private boolean isDualGameOver() {
        var points = countDualPoints();
        return (points[0] == 0 || points[1] == 0 || (points[0] + points[1] == 64));
    }

    /**
     * Начало игры с компьютером
     */
    private void startGame() {
        initializeStartParams();
        drawField("Начало");
        while (true) {
            userMove("user");
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

    /**
     * Метод хода игрока
     * @param player user - первый игрок (дуэль и с компьютером), secondUser - второй игрок в дуэли
     */
    private void userMove(String player) {
        var possiblePositions = Movement.canMove(player);
        userCanMove = possiblePositions.size() != 0;
        if (!userCanMove) {
            InteractMenu.showUserCantMove();
            return;
        }
        drawFieldWithPossibleMoves(possiblePositions);
        var coordsUser = user.makeMove(player);
        var chip = "user".equals(player) ? 'X' : 'Y';
        GlobalFunctions.changeChipColor(chip, coordsUser[0], coordsUser[1], field);
        countChips();
        drawField("Игрок");
    }

    /**
     * Метод хода компьютера
     */
    private void computerMove() {
        computerCanMove = Movement.canMove("computer").size() != 0;
        if (!computerCanMove) {
            InteractMenu.showComputerCantMove();
            return;
        }

        var coordsComputer = computer.makeMove();
        GlobalFunctions.changeChipColor('Y', coordsComputer[0], coordsComputer[1], field);
        countChips();
        drawField("Компьютер");
    }

    /**
     * Вывод всех возможных ходов для игрока в консоль
     * @param positions Список с потенциальными позициями
     */
    private void showAllPossiblePositions(List<Integer[]> positions) {
        System.out.println("Возможные ходы: ");
        for (var position : positions) {
            System.out.print("[" + (position[0] + 1) + " " + (position[1] + 1) + "]" + " ");
        }
        System.out.print("\n");
    }

    /**
     * Предложение игроку отменить ход
     */
    private void cancelMoveSuggestion() {
        InteractMenu.cancelMove();
        String answer = InteractMenu.SCANNER.nextLine();
        if ("/cancel".equals(answer)) {
            cancelMove();
        }

    }

    /**
     * Провека, что игра с компьютером окончена
     * @return true - закончена, false - не закончена
     */
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

    /**
     * Копируем предыдущее поле
     */
    private void copyPrevField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.arraycopy(field[i], 0, prevField[i], 0, FIELD_SIZE);
        }
    }

    /**
     * Подсчет текущего количества очков для двух игроков
     */
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

    /**
     * Отмена хода игрока
     */
    private void cancelMove() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.arraycopy(prevField[i], 0, field[i], 0, FIELD_SIZE);
        }
        drawField("Игрок");
    }

    /**
     * Отрисовка текущего поля
     * @param player user - ход игрока, computer - ход компьютера
     */
    private void drawField(String player) {
        System.out.println("---------------");
        System.out.println("Сейчас ход " + player);
        System.out.println("Компьютер: " + computerPoints + "; Игрок: " + userPoints);
        System.out.println("---------------");
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[j][7 - i] == 'Y') {
                    System.out.print(ANSI_RED + "Y " + ANSI_RESET);
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

    /**
     * Отрисовка поля с потенциальными позициями
     * @param positions потенциальные позиции
     */
    private void drawFieldWithPossibleMoves(List<Integer[]> positions) {
        System.out.println("---------------");
        showAllPossiblePositions(positions);
        System.out.println("---------------");
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[j][7 - i] == 'Y') {
                    System.out.print(ANSI_RED + "Y " + ANSI_RESET);
                } else if (field[j][7 - i] == 'X') {
                    System.out.print(ANSI_PURPLE + "X " + ANSI_RESET);
                } else if (isPossiblePosition(positions, j, 7 - i)) {
                    System.out.print(ANSI_GREEN + "0 " + ANSI_RESET);
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    /**
     * Провека, что текущая координата в списке потенциальных позиций
     * @param positions список всех потенциальных позиций
     * @param x координата по горизонтали
     * @param y координата по вертикали
     * @return true - координата в списке потенциальных, false - координата не в списке потенциальных
     */
    private boolean isPossiblePosition(List<Integer[]> positions, int x, int y) {
        for (var position : positions) {
            if (position[0] == x && position[1] == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Инициализация первоначальных параметров
     */
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

    /**
     * Подсчет очков для дуэли
     * @return список с парой очков
     */
    private Integer[] countDualPoints() {
        int firstUserPoints = 0;
        int secondUserPoints = 0;
        for(var item : field) {
            for(var cell: item) {
                if(cell == 'Y') {
                    secondUserPoints++;
                } else if (cell == 'X'){
                    firstUserPoints++;
                }
            }
        }
        //System.out.println("X: " + firstUserPoints + "; Y: " + secondUserPoints);
        return new Integer[]{firstUserPoints, secondUserPoints};
    }
}
