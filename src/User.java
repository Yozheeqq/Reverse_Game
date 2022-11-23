public class User {
    private int maxPoints;

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int value) {
        if(value < 0 || value > 64) {
            System.out.println("Кол-во очков должно быть неотрицательным числом и меньше 65");
        }
        maxPoints = value;
    }

    public Integer[] makeMove() {
        String[] coords = InteractMenu.SCANNER.nextLine().split(" ");
        // Проверка ввода
        int x = Integer.parseInt(coords[0]) - 1;
        int y = Integer.parseInt(coords[1]) - 1;
        Game.field[x][y] = 'X';
        return new Integer[] {x, y};
    }
}
