package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
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

public class Ex01FirstQueryTest extends JooqTestcontainersTest {

    @Test
    void query() {
        title("A simple query");

        var result = dsl.selectFrom(DSL.dual()).fetch();

        println(result);
    }

    @Test
    void all_actors() {
        title("Find all actors");

        var result = dsl.selectFrom(ACTOR).fetch();

        println(result);
    }

    @Test
    void all_films() {
        title("Find all film titles with the language");

        var result = dsl
                .select(FILM.TITLE, LANGUAGE.NAME)
                .from(FILM)
                .join(LANGUAGE).on(LANGUAGE.LANGUAGE_ID.eq(FILM.LANGUAGE_ID))
                .fetch();

        println(result);
    }

    @Test
    void all_films_implicit() {
        title("Find all film titles with the language using implicit join");

        var result = dsl
                .select(FILM.TITLE, FILM.filmLanguageIdFkey().NAME)
                .from(FILM)
                .fetch();

        println(result);
    }

    @Test
    void all_actors_with_number_of_films() {
        var result = dsl
                .select(FILM_ACTOR.actor().FIRST_NAME,
                        FILM_ACTOR.actor().LAST_NAME,
                        count(FILM_ACTOR.FILM_ID))
                .from(FILM_ACTOR)
                .groupBy(FILM_ACTOR.actor().FIRST_NAME, FILM_ACTOR.actor().LAST_NAME)
                .fetch();

        println(result);
    }

    @Test
    void multiset() {
        var result = dsl
                .select(CATEGORY.NAME,
                        DSL.multiset(
                                select(FILM.TITLE)
                                        .from(FILM)
                                        .join(FILM_CATEGORY).on(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID)
                                        )))
                .from(CATEGORY)
                .fetch();

        println(result);
    }
}
