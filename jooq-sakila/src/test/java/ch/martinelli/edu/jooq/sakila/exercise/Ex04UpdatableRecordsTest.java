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
        title("Save with ActiveRecord");

        var rental = dsl.newRecord(RENTAL);
        rental.setRentalDate(LocalDateTime.now());

        var staffId = dsl
                .select(STAFF.STAFF_ID)
                .from(STAFF)
                .where(STAFF.EMAIL.eq("Mike.Hillyer@sakilastaff.com"))
                .fetchOneInto(Long.class);

        rental.setStaffId(staffId);

        var customerId = dsl
                .select(CUSTOMER.CUSTOMER_ID)
                .from(CUSTOMER)
                .where(CUSTOMER.FIRST_NAME.eq("ANDREA"))
                .and(CUSTOMER.LAST_NAME.eq("HENDERSON"))
                .fetchOneInto(Long.class);

        rental.setCustomerId(customerId);

        var inventoryId = dsl
                .select(INVENTORY.INVENTORY_ID)
                .from(INVENTORY)
                .where(INVENTORY.film().TITLE.eq("STRANGERS GRAFFITI"))
                .and(INVENTORY.INVENTORY_ID.notIn(
                                select(RENTAL.INVENTORY_ID)
                                        .from(RENTAL)
                                        .where(RENTAL.RETURN_DATE.gt(LocalDateTime.now()))
                        )
                )
                .maxRows(1)
                .fetchOneInto(Long.class);

        rental.setInventoryId(inventoryId);

        rental.store();
    }

}
