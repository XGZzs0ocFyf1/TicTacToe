import java.util.Random;

/*
    Class for handling game field. Check, validate turns...
 */
public class GameCore {

    private static int SIZE;
    private static Cell[][] field;

    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';


    public GameCore(int size) {
        SIZE = size;
        initializeGame();
    }

    private static void initializeGame() {
        field = new Cell[SIZE][SIZE];
        for (int rowIdx = 0; rowIdx < SIZE; rowIdx++) {

            for (int colIdx = 0; colIdx < SIZE; colIdx++) {
                field[rowIdx][colIdx] = new Cell(rowIdx, colIdx, DOT_EMPTY + "");
            }
        }
    }

    public Cell[][] getField() {
        return field;
    }

    public boolean checkWin(char symbol) {
        //check rows

        int diagRWins = 0;
        int diagLWins = 0;
        for (int rowIdx = 0; rowIdx < SIZE; rowIdx++) {
            int lineWinVert = 0;
            int lineWinHoriz = 0;
            for (int colIdx = 0; colIdx < SIZE; colIdx++) {

                //check horizontal win
                if (field[rowIdx][colIdx].isIncludeChar(symbol)) lineWinHoriz++;
                if (lineWinHoriz == SIZE) return true;


                //check vertival win
                if (field[colIdx][rowIdx].isIncludeChar(symbol)) lineWinVert++;
                if (lineWinVert == SIZE) return true;
            }
            //check diagonal win
            if (field[rowIdx][rowIdx].isIncludeChar(symbol)) diagRWins++;
            if (field[rowIdx][SIZE - 1 - rowIdx].isIncludeChar(symbol)) diagLWins++;
            if (diagRWins == SIZE || diagLWins == SIZE) return true;
        }

        return false;

    }

    public boolean isMapFull() {
        for (Cell[] rows : field) {
            for (Cell cell : rows) {
                if (cell.isIncludeChar(DOT_EMPTY)) return false;
            }

        }
        return true;
    }




    public boolean isCellValid(int rowIdx, int cellIdx, char symbol) {

        if (isCoordNotValid(rowIdx) || (isCoordNotValid(cellIdx))) {
            return false;
        }

        if (field[rowIdx][cellIdx].isIncludeChar(DOT_O) || field[rowIdx][cellIdx].isIncludeChar(DOT_X)) {
            if (symbol == DOT_X || symbol == DOT_O) {
                return false;
            }
        }

        return true;
    }

    private static boolean isCoordNotValid(int idx) {
        return idx < 0 || idx > SIZE;
    }

}
