import java.util.ArrayList;
import java.util.List;

public class Computer {
    // 1 - новичок
    // 2 - профессионал
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int value) {
        if (value < 0 || value > 2) {
            System.out.println("Уровень должен быть 1 или 2");
            return;
        }
        level = value;
    }

    public Integer[] makeMove() {
        if(getLevel() == 1) {
            return easyLevelMove();
        } else {
            return hardLevelMode();
        }
    }

    private Integer[] easyLevelMove() {
        List<Double[]> coordsWithValues = new ArrayList<>();
        for(int i = 0; i < Game.FIELD_SIZE; i++) {
            for(int j = 0; j < Game.FIELD_SIZE; j++) {
                if(Movement.isCorrectMove(i, j, "computer")) {
                    double funcRes = easyFunction(i, j);
                    Double[] array = {(double) i, (double) j, funcRes};
                    coordsWithValues.add(array);
                }
            }
        }
        int index = findMax(coordsWithValues);

        return new Integer[] {coordsWithValues.get(index)[0].intValue(),
                coordsWithValues.get(index)[1].intValue()};
    }

    private int findMax(final List<Double[]> coordsWithValues) {
        int index = -1;
        double maxValue = 0;
        for(int i = 0; i < coordsWithValues.size(); i++) {
            if(coordsWithValues.get(i)[2] >= maxValue) {
                maxValue = coordsWithValues.get(i)[2];
                index = i;
            }
        }
        return index;
    }

    private double easyFunction(int x, int y) {
        double result = getPositionPoints(x, y, false);
        var list = Movement.getCorrectDirections(x, y);
        for(var item : list) {
            int curX = x, curY = y;
            curX += item[0];
            curY += item[1];
            while(Game.field[curX][curY] != 'X') {
                result += getPositionPoints(curX, curY, true);
                curX += item[0];
                curY += item[1];
            }
        }
        return result;
    }

    private double getPositionPoints(int x, int y, boolean isAnother) {
        if(isAnother) {
            if(x == 0 || y == 0 || x == 7 || y == 7) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if((x + y) % 7 == 0 && (x * y == 0 || x * y == 49)) {
                return 0.8;
            }
            if(x > 0 && x < 7 && y > 0 && y < 7) {
                return 0;
            }
            return 0.4;
        }
    }

    private Integer[] hardLevelMode() {
        // TODO Реализовать сложный уровень
        return new Integer[2];
    }
}
