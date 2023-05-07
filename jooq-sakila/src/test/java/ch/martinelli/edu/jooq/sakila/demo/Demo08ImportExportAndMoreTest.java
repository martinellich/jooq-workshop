package ch.martinelli.edu.jooq.sakila.demo;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.jooq.*;
import ch.martinelli.edu.jooq.sakila.db.tables.records.ActorRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;

import static ch.martinelli.edu.jooq.sakila.db.Tables.ACTOR;
import static ch.martinelli.edu.jooq.sakila.db.tables.Payment.PAYMENT;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.DATE;

class Demo08ImportExportAndMoreTest extends JooqTestcontainersTest {

    @Test
    void importExportCSV() throws IOException {
        title("Importing data from CSV content");
        dsl.loadInto(ACTOR)

                // Throttling flags
                .bulkAfter(2)
                .batchAfter(2)

                // Duplicate handling flags
                .onDuplicateKeyError()

                // Source specification
                .loadCSV(
                        """
                                id;last_name;first_name
                                201;Doe;John
                                202;Smith;Jane
                                203;Colson;Wilda
                                204;Abel;Izzy
                                205;Langdon;Cayson
                                206;Brooke;Deon
                                207;Wolf;Gabriella
                                208;Edie;Drew
                                209;Rupert;Maeghan
                                210;Coleman;Skyler
                                """)
                .fields(ACTOR.ACTOR_ID, ACTOR.LAST_NAME, ACTOR.FIRST_NAME)
                .ignoreRows(1)
                .separator(';')
                .quote('"')
                .execute();

        title("Exporting data to CSV content");
        println(dsl.fetch(ACTOR, ACTOR.ACTOR_ID.gt(200L)).formatCSV());
    }

    @Test
    void importExportJSON() throws IOException {
        title("Importing data from JSON content");
        dsl.loadInto(ACTOR)
                .batchAll()
                .loadJSON(
                        """
                                [
                                  { "id": 201, "lastName": "Doe", "firstName": "John" },
                                  { "id": 202, "lastName": "Smith", "firstName": "Jane" },
                                  { "id": 203, "lastName": "Colson", "firstName": "Wilda" },
                                  { "id": 204, "lastName": "Abel", "firstName": "Izzy" },
                                  { "id": 205, "lastName": "Langdon", "firstName": "Cayson" },
                                  { "id": 206, "lastName": "Brooke", "firstName": "Deon" },
                                  { "id": 207, "lastName": "Wolf", "firstName": "Gabriella" },
                                  { "id": 208, "lastName": "Edie", "firstName": "Drew" },
                                  { "id": 209, "lastName": "Rupert", "firstName": "Maeghan" },
                                  { "id": 210, "lastName": "Coleman", "firstName": "Skyler" }
                                ]
                                """)
                .fields(ACTOR.ACTOR_ID, ACTOR.LAST_NAME, ACTOR.FIRST_NAME)
                .execute();

        println(dsl.fetch(ACTOR, ACTOR.ACTOR_ID.gt(200L)).formatJSON(JSONFormat.DEFAULT_FOR_RESULTS.header(false).format(true)));
    }

    @Test
    void importExportJavaData() throws IOException {
        title("Importing data from Java in-memory content");
        dsl.loadInto(ACTOR)
                .batchAll()
                .loadArrays(new Object[][]{
                        {201, "Doe", "John"},
                        {202, "Smith", "Jane"},
                        {203, "Colson", "Wilda"},
                        {204, "Abel", "Izzy"},
                        {205, "Langdon", "Cayson"},
                        {206, "Brooke", "Deon"},
                        {207, "Wolf", "Gabriella"},
                        {208, "Edie", "Drew"},
                        {209, "Rupert", "Maeghan"},
                        {210, "Coleman", "Skyler"}
                })
                .fields(ACTOR.ACTOR_ID, ACTOR.LAST_NAME, ACTOR.FIRST_NAME)
                .execute();
        println(dsl.fetch(ACTOR, ACTOR.ACTOR_ID.gt(200L)));
    }

    @Test
    void exportXML() {
        // No import supported yet.
        Result<ActorRecord> result = dsl.fetch(ACTOR, ACTOR.ACTOR_ID.lt(4L));
        XMLFormat xmlformat = XMLFormat.DEFAULT_FOR_RESULTS.header(false).format(true);

        title("XML export default record format is VALUE_ELEMENTS_WITH_FIELD_ATTRIBUTE");
        println(result.formatXML(xmlformat));

        title("COLUMN_NAME_ELEMENTS record format");
        println(result.formatXML(xmlformat.recordFormat(XMLFormat.RecordFormat.COLUMN_NAME_ELEMENTS)));

        title("VALUE_ELEMENTS record format");
        println(result.formatXML(xmlformat.recordFormat(XMLFormat.RecordFormat.VALUE_ELEMENTS)));
    }

    @Test
    void exportText() {
        // No import supported yet.
        Result<ActorRecord> result = dsl.fetch(ACTOR, ACTOR.ACTOR_ID.lt(4L));

        title("The default text format is also used when DEBUG logging things");
        println(result.format());

        title("Different ASCII table formatting styles are available");
        println(result.format(TXTFormat.DEFAULT
                .horizontalHeaderBorder(false)
                .intersectLines(false)
                .verticalTableBorder(false)
                .minColWidth(20)
        ));
    }

    @Test
    void exportHTML() {
        title("Could be good enough");
        println(dsl.fetch(ACTOR, ACTOR.ACTOR_ID.lt(4L)).formatHTML());
    }

    @Test
    void exportChart() {
        Field<Date> date = cast(PAYMENT.PAYMENT_DATE, DATE);

        title("Hey, why not! 🤩");
        println(dsl
                .select(date, sum(PAYMENT.AMOUNT))
                .from(PAYMENT)
                .groupBy(date)
                .orderBy(date)
                .fetch()
                .formatChart()
        );

        title("The envy of MS Excel's pivot tables");
        println(dsl
                .select(
                        date,
                        sum(sum(PAYMENT.AMOUNT).filterWhere(PAYMENT.staff().STORE_ID.eq(1L))).over(orderBy(date)),
                        sum(sum(PAYMENT.AMOUNT).filterWhere(PAYMENT.staff().STORE_ID.eq(2L))).over(orderBy(date)))
                .from(PAYMENT)
                .groupBy(date)
                .orderBy(date)
                .fetch()
                .formatChart(ChartFormat.DEFAULT.values(1, 2).display(ChartFormat.Display.STACKED))
        );
    }

    @AfterEach
    void teardown() {
        cleanup(ACTOR, ACTOR.ACTOR_ID);
    }
}
