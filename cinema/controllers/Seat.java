package cinema.controllers;

public class Seat {
    private int row;
    private int column;

    public Seat() {
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getrow() {
        return row;
    }

    public int getcolumn() {
        return column;
    }

    public int getprice(){
        return row <= 4 ? 10 : 8;
    }

    public void setrow(int row) {
        this.row = row;
    }

    public void setcolumn(int column) {
        this.column = column;
    }
}
