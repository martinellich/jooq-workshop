package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static ch.martinelli.edu.jooq.sakila.db.tables.Customer.CUSTOMER;
import static ch.martinelli.edu.jooq.sakila.db.tables.Inventory.INVENTORY;
import static ch.martinelli.edu.jooq.sakila.db.tables.Rental.RENTAL;
import static ch.martinelli.edu.jooq.sakila.db.tables.Staff.STAFF;
import static org.jooq.impl.DSL.select;

class Ex04UpdatableRecordsTest extends JooqTestcontainersTest {

    @Transactional
    @Test
    void save() {
    }

}
