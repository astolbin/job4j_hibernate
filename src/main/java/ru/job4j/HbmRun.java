package ru.job4j;

import ru.job4j.model.Candidate;
import ru.job4j.store.CandidateStore;

public class HbmRun {
    public static void main(String[] args) {
        CandidateStore store = new CandidateStore();

        store.add(new Candidate("candidate 1", 1000, 70000));
        store.add(new Candidate("candidate 2", 2000, 90000));
        store.add(new Candidate("candidate 3", 500, 50000));

        for (Candidate candidate : store.getAll()) {
            System.out.println(candidate);
        }

        Candidate upd = store.getById(1);
        upd.setSalary(75000);
        store.update(1, upd);

        Candidate del = store.getByName("candidate 3").get(0);
        store.delete(del.getId());

        System.out.println();
        for (Candidate candidate : store.getAll()) {
            System.out.println(candidate);
        }
    }
}
