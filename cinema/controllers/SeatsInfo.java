package cinema.controllers;

import java.util.ArrayList;
import java.util.List;

public class SeatsInfo {
    private final int totalRows;
    private final int totalColumns;
    private final boolean[][] seats; // true if available

    public SeatsInfo() {
        totalRows = 9;
        totalColumns = 9;
        seats = new boolean[totalRows][];
        for (int i = 0; i < totalRows; i++) {
            seats[i] = new boolean[totalColumns];
            for (int j = 0; j < totalColumns; j++) {
                seats[i][j] = true;
            }
        }
    }

    public int gettotal_rows() {
        return totalRows;
    }

    public int gettotal_columns() {
        return totalColumns;
    }

    public List<Seat> getavailable_seats() {
        List<Seat> result = new ArrayList<>();
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                if (seats[i][j]) result.add(new Seat(i + 1, j + 1));
            }
        }
        return result;
    }

    public int calcTotalSeats(){
        return totalRows * totalColumns;
    }

    public boolean isInBounds(int row, int column) {
        return row >= 0 && row < totalRows && column >= 0 && column < totalColumns;
    }

    public boolean isAvailable(int row, int column) {
        return seats[row][column];
    }

    public void purchaseSeat(int row, int column) {
        seats[row][column] = false;
    }

    public void returnSeat(int row, int column) {
        seats[row][column] = true;
    }
}
