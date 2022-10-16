package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Theatre;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@ThreadSafe
public class TheatreLayout {
    ConcurrentHashMap<Integer, List<Integer>> allSeats = new ConcurrentHashMap<>();

    public TheatreLayout() {
    }

    public ConcurrentHashMap<Integer, List<Integer>> setAllSeats(Theatre theatre) {
        for (int i = 1; i <= theatre.getRow(); i++) {
            List<Integer> list = new ArrayList<>();
            allSeats.put(i, list);
            for (int j = 1; j <= theatre.getSeat(); j++) {
                list.add(j);
            }
        }
        return allSeats;
    }
}
