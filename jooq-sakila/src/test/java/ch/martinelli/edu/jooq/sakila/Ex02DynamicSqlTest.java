package ch.martinelli.edu.jooq.sakila;

import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.val;

class Ex02DynamicSqlTest extends JooqTestcontainersTest {

    @Test
    void testDynamicSQL() {
        title("Every jOOQ query is a dynamic SQL query. You just don't see it");
        dsl.select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .from(ACTOR)
                .where(ACTOR.ACTOR_ID.in(1L, 2L, 3L))
                .orderBy(ACTOR.FIRST_NAME)
                .limit(5)
                .fetch();

        title("The above and the below are equivalent");
        List<SelectField<?>> select = List.of(ACTOR.FIRST_NAME, ACTOR.LAST_NAME);
        Table<?> from = ACTOR;
        Condition where = ACTOR.ACTOR_ID.in(1L, 2L, 3L);
        List<OrderField<?>> orderBy = List.of(ACTOR.FIRST_NAME);
        Field<Integer> limit = val(5);

        dsl.select(select)
                .from(from)
                .where(where)
                .orderBy(orderBy)
                .limit(limit)
                .fetch();

        title("Any 'static' query part can be replaced by an expression, function call, etc.");
        List<Integer> ids = List.of(1, 2, 3);

        dsl.select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .from(ACTOR)
                .where(ids.isEmpty()
                        ? noCondition()
                        : ACTOR.ACTOR_ID.in(ids.stream().map(Long::valueOf).map(DSL::val).toList()))
                .orderBy(ACTOR.FIRST_NAME)
                .limit(5)
                .fetch();
    }

    @Test
    void generateQueryParts() {
        println(reduceCondition(List.of()));
        println(reduceCondition(List.of(1)));
        println(reduceCondition(List.of(1, 2, 3)));
    }

    @NotNull
    private Condition reduceCondition(List<Integer> ids) {
        title("List: " + ids);
        return ids
                .stream()
                .map(Long::valueOf)
                .map(ACTOR.ACTOR_ID::eq)
                .reduce(noCondition(), Condition::or);
    }
}
