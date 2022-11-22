public class Game {
    public void showMenu() {
        System.out.println("""
                Это игра Реверси. Для продолжения выберите дейтсвие
                1. Игра с компьютером
                2. Игра вдвоем
                3. Показать максимум очков
                4. Выход
                """);
    }

    public void showIncorrectInput() {
        System.out.println("Неверный ввод");
    }

    public void showGoodBye() {
        System.out.println("До связи!");
    }
}
