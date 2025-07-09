package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static ch.martinelli.edu.jooq.sakila.db.tables.Rental.RENTAL;

class Ex07LockingAndTrxTest extends JooqTestcontainersTest {

    @Test
    void optimistic_locking() throws InterruptedException {
        var thread1 = new Thread(() ->
                dsl.selectFrom(RENTAL)
                        .where(RENTAL.RENTAL_ID.eq(854L))
                        .fetchOptional()
                        .ifPresent(rental -> {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ignored) {
                            }
                            rental.setRentalDate(LocalDateTime.now());
                            rental.store();
                        }));

        var thread2 = new Thread(() ->
                dsl.selectFrom(RENTAL)
                        .where(RENTAL.RENTAL_ID.eq(854L))
                        .fetchOptional()
                        .ifPresent(rental -> {
                            rental.setRentalDate(LocalDateTime.now());
                            rental.store();
                        }));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

}
