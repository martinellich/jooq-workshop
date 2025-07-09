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
        title("Fetch by rental date");

        var rentalDao = new RentalDao(dsl.configuration());
        var rentals = rentalDao.fetchByRentalDate(LocalDateTime.now());

        println(rentals);
    }

    @Transactional
    @Test
    void update_rental_date() {
        title("Update record");

        var rentalDao = new RentalDao(dsl.configuration());

        rentalDao.findOptionalById(854L).ifPresent(rental -> {
            rental.setRentalDate(LocalDateTime.now());
            rentalDao.update(rental);
        });
    }

    @Transactional
    @Test
    void delete_by_id() {
        title("Delete by id");

        try {
            var rentalDao = new RentalDao(dsl.configuration());
            rentalDao.deleteById(854L);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }
}
