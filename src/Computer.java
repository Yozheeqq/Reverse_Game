
public final class Computer {
    // 1 - новичок
    // 2 - профессионал
    private int level;

    /**
     * Получить уровень компьютера
     * @return уровень компьютера. 1 - легкий, 2 - тяжелый
     */
    public int getLevel() {
        return level;
    }

    /**
     * Установить уровень компьютера
     * @param value 1 - легкий, 2 - тяжелый
     */
    public void setLevel(int value) {
        if (value < 0 || value > 2) {
            System.out.println("Уровень должен быть 1 или 2");
            return;
        }
        level = value;
    }

    /**
     * Метод для хода компьютера
     * @return позиция, куда сходил компьютер
     */
    public Integer[] makeMove() {
        if (getLevel() == 1) {
            return ComputerFunctions.easyLevelMove("computer");
        } else if (getLevel() == 2) {
            return ComputerFunctions.hardLevelMode();
        }
        return new Integer[2];
    }
}
