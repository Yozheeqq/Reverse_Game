import java.util.ArrayList;
import java.util.List;

/**
 * Класс для хода компьютера
 */
public class ComputerFunctions {
    /**
     * Запрещаем создавать инстанс класс
     */
    private ComputerFunctions() {}

    /**
     * Получаем кол-во очков за текушую фишку
     * @param x координата по горизонтали текущей фишки
     * @param y координата по вертикали текущей фишки
     * @param isAnother true - фишка противника, false - фишка компьютера
     * @return кол-во очков за позицию
     */
    private static double getPositionPoints(int x, int y, boolean isAnother) {
        if (isAnother) {
            if (x == 0 || y == 0 || x == 7 || y == 7) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if ((x + y) % 7 == 0 && (x * y == 0 || x * y == 49)) {
                return 0.8;
            }
            if (x > 0 && x < 7 && y > 0 && y < 7) {
                return 0;
            }
            return 0.4;
        }
    }

    /**
     * Поиск наибольшего значения для функции
     * @param coordsWithValues список всех потенциальных ходов
     * @return индекс наибольшего значения
     */
    private static int findMax(final List<Double[]> coordsWithValues) {
        int index = -1;
        double maxValue = -1000;
        for (int i = 0; i < coordsWithValues.size(); i++) {
            if (coordsWithValues.get(i)[2] >= maxValue) {
                maxValue = coordsWithValues.get(i)[2];
                index = i;
            }
        }
        return index;
    }

    /**
     * Ход для легкого уровня
     * @param player user - игрок, computer - компьютер
     * @return Координаты хода компьютера
     */
    public static Integer[] easyLevelMove(String player) {
        List<Double[]> coordsWithValues = new ArrayList<>();
        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            for (int j = 0; j < Game.FIELD_SIZE; j++) {
                if (Movement.isCorrectMove(i, j, player)) {
                    double funcRes = ComputerFunctions.easyFunction(i, j);
                    Double[] array = {(double) i, (double) j, funcRes};
                    coordsWithValues.add(array);
                }
            }
        }
        int index = findMax(coordsWithValues);
        return new Integer[]{coordsWithValues.get(index)[0].intValue(),
                coordsWithValues.get(index)[1].intValue()};
    }

    /**
     * Ход для сложного уровня
     * @return координаты хода компьютера
     */
    public static Integer[] hardLevelMode() {
        List<Double[]> coordsWithValues = new ArrayList<>();
        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            for (int j = 0; j < Game.FIELD_SIZE; j++) {
                if (Movement.isCorrectMove(i, j, "computer")) {
                    double funcRes = ComputerFunctions.hardFunction(i, j);
                    Double[] array = {(double) i, (double) j, funcRes};
                    coordsWithValues.add(array);
                }
            }
        }
        int index = findMax(coordsWithValues);
        return new Integer[]{coordsWithValues.get(index)[0].intValue(),
                coordsWithValues.get(index)[1].intValue()};
    }

    /**
     * Функция подсчета для легкого хода
     * @param x координата по горизонтали текущей фишки
     * @param y координата по вертикали текущей фишки
     * @return значение функции
     */
    public static double easyFunction(int x, int y) {
        double result = getPositionPoints(x, y, false);
        var list = Movement.getCorrectDirections(x, y);
        for (var item : list) {
            int curX = x, curY = y;
            curX += item[0];
            curY += item[1];
            while (Game.field[curX][curY] != 'X') {
                result += getPositionPoints(curX, curY, true);
                curX += item[0];
                curY += item[1];
            }
        }
        return result;
    }

    /**
     * Функция подсчета для сложного хода
     * @param x координата по горизонтали текущей фишки
     * @param y координата по вертикали текущей фишки
     * @return значение функции
     */
    public static double hardFunction(int x, int y) {
        double computerValue = easyFunction(x, y);
        char[][] field = new char[8][8];
        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            System.arraycopy(Game.field[i], 0, field[i], 0, Game.FIELD_SIZE);
        }
        GlobalFunctions.changeChipColor('Y', x, y, field);
        if (Movement.canMove("user").size() == 0) {
            return computerValue;
        }
        var playerPos = easyLevelMove("user");
        var playerPoints = easyFunction(playerPos[0], playerPos[1]);
        return computerValue - playerPoints;
    }
}
