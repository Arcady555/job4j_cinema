package ru.job4j.persistence.model;

import java.util.Objects;

public class Ticket {
    private int id;
    private int movieId;
    private int row;
    private int cell;

    public Ticket() {
    }

    public Ticket(int id, int movieId, int row, int cell) {
        this.id = id;
        this.movieId = movieId;
        this.row = row;
        this.cell = cell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

 /**   public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    } */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return movieId == ticket.movieId && row == ticket.row && cell == ticket.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, row, cell);
    }
}
