
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
        if (getLevel() == 1) {
            return ComputerFunctions.easyLevelMove("computer");
        } else if (getLevel() == 2) {
            return ComputerFunctions.hardLevelMode();
        }
        return new Integer[2];
    }
}
