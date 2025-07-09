package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import ch.martinelli.edu.jooq.sakila.exercise.records.ActorWithFilmCount;
import ch.martinelli.edu.jooq.sakila.exercise.records.CategoryWithFilms;
import ch.martinelli.edu.jooq.sakila.exercise.records.FilmAndLength;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.Tables.FILM_ACTOR;
import static ch.martinelli.edu.jooq.sakila.db.tables.Category.CATEGORY;
import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;
import static ch.martinelli.edu.jooq.sakila.db.tables.FilmCategory.FILM_CATEGORY;
import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.select;

class Ex05PojosTest extends JooqTestcontainersTest {

    @Test
    void film_and_length() {
        title("Fetch films with length into Java Record");

        var result = dsl
                .select(FILM.TITLE, FILM.LENGTH)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchInto(FilmAndLength.class);

        println(result);
    }

    @Test
    void all_actors_with_number_of_films_fetch_into_record() {
        title("Fetch actors first and last name with number of films into Java Record");

        var result = dsl
                .select(FILM_ACTOR.actor().FIRST_NAME,
                        FILM_ACTOR.actor().LAST_NAME,
                        count(FILM_ACTOR.FILM_ID))
                .from(FILM_ACTOR)
                .groupBy(FILM_ACTOR.actor().FIRST_NAME, FILM_ACTOR.actor().LAST_NAME)
                .fetchInto(ActorWithFilmCount.class);

        println(result);
    }

    @Test
    void all_actors_with_number_of_films_mapping() {
        title("Fetch actors first and last name with number of films into Java Record using mapping");

        var result = dsl
                .select(FILM_ACTOR.actor().FIRST_NAME,
                        FILM_ACTOR.actor().LAST_NAME,
                        count(FILM_ACTOR.FILM_ID))
                .from(FILM_ACTOR)
                .groupBy(FILM_ACTOR.actor().FIRST_NAME, FILM_ACTOR.actor().LAST_NAME)
                .fetch(mapping(ActorWithFilmCount::new));

        println(result);
    }


    @Test
    void multiset() {
        title("Fetch categories with a list of films");

        var result = dsl
                .select(CATEGORY.NAME,
                        DSL.multiset(
                                select(FILM.TITLE)
                                        .from(FILM)
                                        .join(FILM_CATEGORY).on(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID)
                                        )
                        ).convertFrom(r -> r.map(mapping(CategoryWithFilms.Film::new)))
                )
                .from(CATEGORY)
                .fetch(mapping(CategoryWithFilms::new));

        println(result);
    }
}
