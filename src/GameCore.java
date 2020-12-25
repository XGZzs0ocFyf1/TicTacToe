import java.util.Random;


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
        int lineWinHoriz = 0;

        for (int rowIdx = 0; rowIdx < SIZE; rowIdx++) {
            for (int colIdx = 0; colIdx < SIZE; colIdx++) {
                if (field[rowIdx][colIdx].isIncludeChar(symbol)) lineWinHoriz++;
                if (lineWinHoriz == SIZE) {
                    System.out.println("Horiz win");
                    return true;
                }
            }
            //reset to zero horizontal counter
            lineWinHoriz = 0;

        }

        int lineWinVert = 0;
        for (int colIdx = 0; colIdx < SIZE; colIdx++) {
            for (int rowIdx = 0; rowIdx < SIZE; rowIdx++) {
                if (field[rowIdx][colIdx].isIncludeChar(symbol)) lineWinVert++;
                if (lineWinVert == SIZE) {
                    System.out.println("Vertical win");
                }
            }
            lineWinVert = 0;
        }


        //check diagonals
        int diagRWins = 0;
        int diagLWins = 0;
        for (int rowIdx = 0; rowIdx < SIZE; rowIdx++) {
            if (field[rowIdx][rowIdx].isIncludeChar(symbol)) diagRWins++;

            if (field[rowIdx][SIZE - 1 - rowIdx].isIncludeChar(symbol)) diagLWins++;
            System.out.printf("rowIdx = %s  diaglIdx = %s diagRindex = %s \n", rowIdx, diagLWins, diagRWins);
            if (diagRWins == SIZE || diagLWins == SIZE) {
                System.out.println("diag wins");
                System.out.println("diag wins");

                return true;
            }

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

    public void computerTurn() {

        int rowIdx = -1;
        int colIdx = -1;

        System.out.println("Ход компьютера");
        Random rand = new Random(2);
        do {
            rowIdx = rand.nextInt(SIZE);
            colIdx = rand.nextInt(SIZE);
        } while (!isCellValid(rowIdx, colIdx, DOT_O));

        System.out.printf("компьютер походил на строка = %s столбец=  %s \n", rowIdx, colIdx);
        field[rowIdx][colIdx] = new Cell(rowIdx, colIdx, DOT_O + "");

    }

    private static void printHeader() {
        for (int rowIndex = 0; rowIndex <= SIZE; rowIndex++) {
            System.out.print(rowIndex + " ");
        }
        System.out.println();


    }

    public boolean isCellValid(int rowIdx, int cellIdx, char symbol) {
        System.out.println("Валидирую...");
        if (isCoordNotValid(rowIdx) || (isCoordNotValid(cellIdx))) {
            System.out.println("Индексы строк и стробцов должны быть в диапазоне от 0 до " + SIZE + ".");
            return false;
        }

        if (field[rowIdx][cellIdx].isIncludeChar(DOT_O) || field[rowIdx][cellIdx].isIncludeChar(DOT_X)) {
            if (symbol == DOT_X || symbol == DOT_O) { // AI debug  //|| symbol == DOT_O
                System.out.printf("Клетка с координатами [%s, %s] уже занята\n", rowIdx, cellIdx);
                return false;
            }
        }
        System.out.println("ячейка валидна");
        return true;
    }

    private static boolean isCoordNotValid(int idx) {
        return idx < 0 || idx > SIZE;
    }

    public void printMap() {
        printHeader();
        for (int rowIdx = 0; rowIdx < SIZE; rowIdx++) {
            System.out.print(rowIdx + 1 + " ");
            for (int colIdx = 0; colIdx < SIZE; colIdx++) {
                System.out.print(field[rowIdx][colIdx] + " ");
            }
            System.out.println();
        }

    }


}
