/**
 * Класс для функционала игры
 */
public final class GlobalFunctions {

    /**
     * Меняем цвет фишек после хода
     * @param currentChip X - фишка игрока, Y - фишка компьютера
     * @param x координата по горизонтали текущей фишки
     * @param y координата по вертикали текущей фишки
     * @param field текущее игровое поле
     */
    public static void changeChipColor(char currentChip, int x, int y, char[][] field) {
        field[x][y] = currentChip;
        changeChipColorHorizontal(currentChip, x, y, field);
        changeChipColorVertical(currentChip, x, y, field);
        changeChipColorDiagonal(currentChip, x, y, field);
    }

    /**
     * Меняем цвет фишек после хода  по диагонали
     * @param currentChip X - фишка игрока, Y - фишка компьютера
     * @param x координата по горизонтали текущей фишки
     * @param y координата по вертикали текущей фишки
     * @param field текущее игровое поле
     */
    private static void changeChipColorDiagonal(char currentChip, int x, int y, char[][] field) {
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k < x && l < y; k++, l++) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for (int i = x + 1, j = y + 1; i < Game.FIELD_SIZE && j < Game.FIELD_SIZE; i++, j++) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k >= x && l >= y; k--, l--) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for (int i = x - 1, j = y + 1; i >= 0 && j < Game.FIELD_SIZE; i--, j++) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k < x && l >= y; k++, l--) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
        for (int i = x + 1, j = y - 1; i < Game.FIELD_SIZE && j >= 0; i++, j--) {
            if (field[i][j] == '0') {
                break;
            }
            if (field[i][j] == currentChip) {
                for (int k = i, l = j; k >= x && l < y; k--, l++) {
                    field[k][l] = currentChip;
                }
                break;
            }
        }
    }

    /**
     * Меняем цвет фишек после хода  по горизонтали
     * @param currentChip X - фишка игрока, Y - фишка компьютера
     * @param x координата по горизонтали текущей фишки
     * @param y координата по вертикали текущей фишки
     * @param field текущее игровое поле
     */
    private static void changeChipColorHorizontal(char currentChip, int x, int y, char[][] field) {
        for (int i = x - 1; i >= 0; i--) {
            if (field[i][y] == '0') {
                break;
            }
            if (field[i][y] == currentChip) {
                for (int j = i; j < x; j++) {
                    field[j][y] = currentChip;
                }
                break;
            }
        }
        for (int i = x + 1; i < Game.FIELD_SIZE; i++) {
            if (field[i][y] == '0') {
                break;
            }
            if (field[i][y] == currentChip) {
                for (int j = x; j <= i; j++) {
                    field[j][y] = currentChip;
                }
                break;
            }
        }
    }

    /**
     * Меняем цвет фишек после хода  по вертикали
     * @param currentChip X - фишка игрока, Y - фишка компьютера
     * @param x координата по горизонтали текущей фишки
     * @param y координата по вертикали текущей фишки
     * @param field текущее игровое поле
     */
    private static void changeChipColorVertical(char currentChip, int x, int y, char[][] field) {
        for (int i = y - 1; i >= 0; i--) {
            if (field[x][i] == '0') {
                break;
            }
            if (field[x][i] == currentChip) {
                for (int j = i; j < y; j++) {
                    field[x][j] = currentChip;
                }
                break;
            }
        }
        for (int i = y + 1; i < Game.FIELD_SIZE; i++) {
            if (field[x][i] == '0') {
                break;
            }
            if (field[x][i] == currentChip) {
                for (int j = y; j <= i; j++) {
                    field[x][j] = currentChip;
                }
                break;
            }
        }
    }
}
