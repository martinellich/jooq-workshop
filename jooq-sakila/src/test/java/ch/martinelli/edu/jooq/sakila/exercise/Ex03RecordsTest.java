package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;

class Ex03RecordsTest extends JooqTestcontainersTest {

    @Test
    void film_title_order_by_length() {
        title("Use RecordHandler (deprecated)");

        dsl
                .select(FILM.TITLE, FILM.LENGTH)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchInto(record -> System.out.println(record.get(FILM.TITLE)));
    }

    @Test
    void only_ids() {
        title("Return a list of longs");

        var result = dsl
                .select(FILM.FILM_ID)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchInto(Long.class);

        println(result);
    }

    @Test
    void stream() {
        title("Fetch to stream");

        dsl
                .select(FILM.FILM_ID)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .stream()
                .forEach(JooqTestcontainersTest::println);
    }

    @Test
    void lazy() {
        title("Fetch lazy");

        try (var cursor = dsl
                .select(FILM.FILM_ID)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchLazy()) {

            while (cursor.hasNext()) {
                println(cursor.fetchNext());
            }
        }
    }
}
