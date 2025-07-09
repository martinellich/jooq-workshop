package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.tables.Category.CATEGORY;

class Ex09ExportTest extends JooqTestcontainersTest {

    @Test
    void export_to_csv() {
        title("Export categories to CSV");

        var csv = dsl.selectFrom(CATEGORY).fetch().formatCSV();

        println(csv);
    }
}
