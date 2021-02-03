package com.spring_boot.domain.review.repository

import com.spring_boot.domain.review.ReviewTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReviewRepositoryTest {

    @Test
    fun testFindAllByBookId() {
        val entity = ReviewTest.entity().save()
        val entity2 = ReviewTest.entity2()
        // set the same book as [entity] and save
        entity2.book = entity.book
        val saved = entity2.save()

        // --------------------------------------

        val entities = ReviewRepository.findAllByBookId(entity.book.id())

        // --------------------------------------

        // should contains the entity which has the BookId passed
        assertTrue(entities.containsAll(listOf(entity, saved)))
    }
}