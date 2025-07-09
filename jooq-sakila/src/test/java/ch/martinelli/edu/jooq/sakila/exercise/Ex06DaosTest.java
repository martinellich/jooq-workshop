package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import ch.martinelli.edu.jooq.sakila.db.tables.daos.RentalDao;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

class Ex06DaosTest extends JooqTestcontainersTest {

    @Test
    void read_by_rental_date() {
    }

    @Transactional
    @Test
    void update_rental_date() {
    }

    @Transactional
    @Test
    void delete_by_id() {
    }
}
