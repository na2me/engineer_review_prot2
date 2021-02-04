package com.spring_boot.domain.review.repository

import com.spring_boot.base.AbstractRepositoryTest
import com.spring_boot.domain.review.ReviewTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReviewRepositoryTest : AbstractRepositoryTest() {

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