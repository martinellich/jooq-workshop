package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import ch.martinelli.edu.jooq.sakila.db.tables.Actor;
import ch.martinelli.edu.jooq.sakila.db.tables.Category;
import ch.martinelli.edu.jooq.sakila.db.tables.Film;
import ch.martinelli.edu.jooq.sakila.db.tables.FilmCategory;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.Tables.FILM_ACTOR;
import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;
import static ch.martinelli.edu.jooq.sakila.db.tables.Category.CATEGORY;
import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;
import static ch.martinelli.edu.jooq.sakila.db.tables.FilmCategory.FILM_CATEGORY;
import static ch.martinelli.edu.jooq.sakila.db.tables.Language.LANGUAGE;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.select;

class Ex01FirstQueryTest extends JooqTestcontainersTest {

    @Test
    void query() {
        title("A simple query");

        var result = dsl.selectFrom(DSL.dual()).fetch();

        println(result);
    }

    @Test
    void all_actors() {
    }

    @Test
    void all_films() {
    }

    @Test
    void all_films_implicit() {
    }

    @Test
    void all_actors_with_number_of_films() {
    }

    @Test
    void multiset() {
    }
}
