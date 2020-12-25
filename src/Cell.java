import java.util.Objects;

public class Cell {
    private final int side = 100;

    private int x;
    private int y;
    private String cellValue;


    public Cell(int x, int y, String cellValue) {
        this.x = x;
        this.y = y;
        this.cellValue = cellValue;
    }

    public boolean isItYou(int x, int y) {
        if (x < 0 || y < 0) return false;
        int startX = (x/side)*side;
        int startY = (y/side)*side;

        if (this.x >= startX && this.x < startX+side && this.y >= startY && this.y < startY+side){
            System.out.println("x = "+x);
            System.out.println("y = "+y);
            System.out.println("startX = "+startX);
            System.out.println("startY = " +startY);
            System.out.println("isItYou::x = "+startX+" y = "+startY);
            return true;
        }

        return false;

    }


    public boolean isIncludeChar(char symbol){
        return this.getCellValue().equals(symbol+"");
    }

   /* @Override
    public String toString() {
        return "Cell{" +
                "side=" + side +
                ", x=" + x +
                ", y=" + y +
                '}';
    }*/

    @Override
    public String toString() {
        return cellValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return side == cell.side &&
                x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, x, y);
    }


    public int getSide() {
        return side;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }


}
