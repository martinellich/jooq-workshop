package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import ch.martinelli.edu.jooq.sakila.db.tables.Category;
import ch.martinelli.edu.jooq.sakila.entitites.CategoryEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

class Ex08JPATest extends JooqTestcontainersTest {

    @Autowired
    private EntityManager em;

    @Transactional
    @Test
    void native_query() {
        title("Use jOOQ to create a native query");

        var sql = dsl.selectFrom(Category.CATEGORY).getSQL();

        List<CategoryEntity> categories = em.createNativeQuery(sql, CategoryEntity.class).getResultList();

        println(categories);
    }
}
