import java.util.Scanner;

public class InteractMenu {
    public static final Scanner SCANNER = new Scanner(System.in);

    private InteractMenu() {}

    public static void showMenu() {
        System.out.println("""
                Это игра Реверси. Для продолжения выберите дейтсвие
                1. Игра с компьютером
                2. Игра вдвоем
                3. Показать максимум очков
                4. Выход
                """);
    }

    public static int chooseLevel() {
        System.out.println("""
                Выберите уровень
                1. Новичок
                2. Профессионал
                """);
        String answer;
        boolean isCorrect = false;
        do{
            answer = SCANNER.nextLine();
            if("1".equals(answer) || "2".equals(answer)) {
                isCorrect = true;
            } else {
                InteractMenu.showIncorrectInput();
            }
        } while(!isCorrect);
        return Integer.parseInt(answer);
    }

    public static void showIncorrectInput() {
        System.out.println("Неверный ввод");
    }

    public static void showGoodBye() {
        System.out.println("До связи!");
    }

    public static void showHowToUseInterface() {
        System.out.println("Введите координаты хода в формате 'x y', где \n" +
                "x и y в диапазоне [1; 8]");
    }

    public static void cancelMove() {
        System.out.println("Отменить ход - /cancel");
    }
}
