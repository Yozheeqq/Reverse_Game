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
}
