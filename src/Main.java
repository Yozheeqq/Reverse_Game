public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        int returnValue = -1;
        while(returnValue != 1) {
            returnValue = game.showMenu();
        }
    }
}