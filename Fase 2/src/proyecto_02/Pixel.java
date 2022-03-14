package proyecto_02;

/**
 *
 * @author Alexis
 */
public class Pixel {
    int row = 0;
    int column = 0;
    String color = "";
    
    public Pixel(int row, int column, String color){
        this.row = row;
        this.column = column;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
