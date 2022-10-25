package ru.job4j.model;

import java.util.Objects;

public class Movie {
    private int id;
    private String logo;
    private String name;

    public Movie() {
    }

    public Movie(int id, String logo, String name) {
        this.id = id;
        this.logo = logo;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return id == movie.id && Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
