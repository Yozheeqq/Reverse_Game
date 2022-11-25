import java.util.ArrayList;
import java.util.List;

public class ComputerFunctions {
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
