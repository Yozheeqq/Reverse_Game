public class Main {

    /**
     * Точка входа программы
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Game game = new Game();
        int returnValue = -1;
        while(returnValue != 1) {
            returnValue = game.showMenu();
        }
    }
}