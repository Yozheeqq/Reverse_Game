import java.util.ArrayList;
import java.util.List;

public class Movement {
    private Movement() {}

    public static boolean isCorrectMove(int x, int y, String player) {
        char yourChip = "user".equals(player) ? 'X' : 'Y';
        char oppositeChip = "user".equals(player) ? 'Y' : 'X';
        Integer[][] potentialPositions = getPotentialPositions(x, y);
        if(Game.field[x][y] != '0') {
            return false;
        }
        for(int i = 0; i < 9; i++) {
            int potentialX = potentialPositions[i][0];
            int potentialY = potentialPositions[i][1];
            if(isCorrectArrayIndex(potentialX, potentialY) &&
                    Game.field[potentialX][potentialY] == oppositeChip) {
                if(isYourChipOnTheWay(x, y,
                        potentialX - x, potentialY - y, yourChip)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isCorrectArrayIndex (int x, int y) {
        return (x >= 0 && x < Game.FIELD_SIZE) && (y >= 0 && y < Game.FIELD_SIZE);
    }

    private static boolean isYourChipOnTheWay(int x, int y, int xDiffer, int yDiffer, char chip) {
        int boundX, boundY;
        boundX = xDiffer >= 0 ? Game.FIELD_SIZE : -1;
        boundY = yDiffer >= 0 ? Game.FIELD_SIZE : -1;
        x += xDiffer;
        y += yDiffer;
        for(int i = x, j = y; i != boundX && j != boundY; i += xDiffer, j += yDiffer) {
            if(Game.field[i][j] == chip){
                return true;
            }
            if(Game.field[i][j] == '0') {
                return false;
            }
        }
        return false;
    }

    public static boolean canMove(String player) {
        for(int i = 0; i < Game.FIELD_SIZE; i++) {
            for(int j = 0; j < Game.FIELD_SIZE; j++) {
                if(Game.field[i][j] == '0') {
                    if(isCorrectMove(i, j, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<Integer[]> getCorrectDirections(int x, int y) {
        List<Integer[]> correctDirections = new ArrayList<>();
        Integer[][] potentialPositions = getPotentialPositions(x, y);
        for(int i = 0; i < 9; i++) {
            int potentialX = potentialPositions[i][0];
            int potentialY = potentialPositions[i][1];
            if(potentialX == x && potentialY == y) {
                continue;
            }
            if(isCorrectArrayIndex(potentialX, potentialY) &&
                    Game.field[potentialX][potentialY] == 'X') {
                if(isYourChipOnTheWay(x, y,
                        potentialX - x, potentialY - y, 'Y')) {
                    correctDirections.add(new Integer[]{potentialX - x, potentialY - y});
                }
            }
        }
        return correctDirections;
    }

    private static Integer[][] getPotentialPositions(int x, int y) {
        Integer[][] potentialPositions = new Integer[9][2];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                potentialPositions[i * 3 + j][0] = x + 1 - i;
                potentialPositions[i * 3 + j][1] = y + 1 - j;
            }
        }
        return potentialPositions;
    }
}
