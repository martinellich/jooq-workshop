package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;
import static ch.martinelli.edu.jooq.sakila.db.tables.Customer.CUSTOMER;
import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;

class Ex02ReturnTypesTest extends JooqTestcontainersTest {

    @Test
    void first_last_name_of_first_actor() {
        title("Find first and last name of actors");

        var result = dsl
                .select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .from(ACTOR)
                .fetch();

        println(result);
    }

    @Test
    void films_ordered() {
        title("Find all films titles ordered by release year");

        var result = dsl
                .selectFrom(FILM)
                .orderBy(FILM.RELEASE_YEAR.desc())
                .offset(1)
                .limit(500)
                .fetch();

        println(result);
    }

    @Test
    void optional_customer() {
        title("Find customer by name as optional");

        var result = dsl
                .selectFrom(CUSTOMER)
                .where(CUSTOMER.FIRST_NAME.eq("Simon"))
                .fetchOptional();

        println(result);
    }
}
