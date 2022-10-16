package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.persistence.MovieDbStore;
import ru.job4j.model.Movie;

import java.util.List;

@Service
@ThreadSafe
public class MovieService {
    private final MovieDbStore store;

    public MovieService(MovieDbStore store) {
        this.store = store;
    }

    public List<Movie> findAll() {
        return store.findAll();
    }

    public void add(Movie movie) {
        store.add(movie);
    }

    public Movie findById(int id) {
        return  store.findById(id);
    }
}
