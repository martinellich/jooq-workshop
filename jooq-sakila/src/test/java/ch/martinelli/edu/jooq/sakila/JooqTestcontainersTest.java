package ch.martinelli.edu.jooq.sakila;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public abstract class JooqTestcontainersTest {

    @Autowired
    protected DSLContext dsl;

    public static void title(String title) {
        println("");
        println(title);
        println("-".repeat((String.valueOf(title)).length()));
    }

    public static <T> void println(T t) {
        System.out.println(t);
    }

    protected void cleanup(Table<?> actor, Field<Long> actorId) {
        dsl.delete(actor)
                .where(actorId.gt(200L))
                .execute();
    }
}
