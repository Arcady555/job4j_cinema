package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.persistence.TheatreLayout;
import ru.job4j.persistence.model.Theatre;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ThreadSafe
public class TheatreService {
    private final ConcurrentHashMap<Integer, List<Integer>> allSeats = new ConcurrentHashMap<>();

    public TheatreService() {
        Theatre theatre = new Theatre(15, 20);
        TheatreLayout theatreLayout = new TheatreLayout();
        for (int i = 1; i <= theatre.getRow(); i++) {
            List<Integer> list = new ArrayList<>();
            allSeats.put(i, list);
            for (int j = 1; j <= theatre.getSeat(); j++) {
                list.add(j);
            }
        }
    }

    public Enumeration<Integer> getRows() {
        return allSeats.keys();
    }

    public List<Integer> getSeats() {
        return allSeats.get(1);
    }
}