/**
 * Класс игрока
 */
public final class User {
    /**
     * Максмальное кол-во очков игрока за текущую сессию
     */
    private int maxPoints;

    /**
     * Получить максмальное число очков
     * @return максмальное число очков
     */
    public int getMaxPoints() {
        return maxPoints;
    }

    /**
     * Установить максимальное число очков
     * @param value кол-во очков
     */
    public void setMaxPoints(int value) {
        if(value < 0 || value > 64) {
            System.out.println("Кол-во очков должно быть неотрицательным числом и меньше 65");
        }
        maxPoints = Math.max(maxPoints, value);
    }

    /**
     * Метод для хода игрока
     * @param player user - первый игрок, secondUser - второй игрок в дуэли
     * @return координаты хода
     */
    public Integer[] makeMove(String player) {
        String[] coords;
        boolean flag;
        int x = 0, y = 0;
        do {
            flag = true;
            coords = InteractMenu.SCANNER.nextLine().split(" ");
            if(!isCorrectInputFormat(coords)) {
                InteractMenu.showIncorrectFormat();
                flag = false;
                continue;
            }
            x = Integer.parseInt(coords[0]) - 1;
            y = Integer.parseInt(coords[1]) - 1;
            if(!Movement.isCorrectMove(x, y, player)) {
                flag = false;
                InteractMenu.showIncorrectPosition();
            }
        } while(!flag);

        return new Integer[] {x, y};
    }

    /**
     * Проверка, что введенные координаты корректны
     * @param input распарсенная строка ввода
     * @return true - ход некорректный, false - ход корректный
     */
    private boolean isCorrectInputFormat(final String[] input) {
        boolean isCorrect = true;
        if(input.length != 2) {
            return false;
        }
        try{
            int x = Integer.parseInt(input[0]) - 1;
            int y = Integer.parseInt(input[1]) - 1;
            if(!Movement.isCorrectArrayIndex(x, y)) {
                isCorrect = false;
            }
        } catch(NumberFormatException exception) {
            isCorrect = false;
        }
        return isCorrect;
    }
}
