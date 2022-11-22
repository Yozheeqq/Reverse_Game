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
}
