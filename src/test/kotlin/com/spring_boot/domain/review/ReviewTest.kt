package com.spring_boot.domain.review

import com.spring_boot.domain.account.AccountTest
import com.spring_boot.domain.book.BookTest
import com.spring_boot.domain.book.repository.BookRepository
import com.spring_boot.domain.review.value_object.ReviewScore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReviewTest {

    @Test
    fun testReCalculateBookScore() {
        // save two reviews both of which refer the same Book
        val review1 = entity().save()
        val review2 = entity2()
        review2.book = review1.book
        review2.save()

        // Book's score should be average of two reviews above
        val averageScore = (review1.score.value + review2.score.value)/2
        val fetchedBook = BookRepository.findById(review1.book.id())
        assertEquals(averageScore, fetchedBook.score.value)
    }

    companion object {
        /**
         * Value Object generation methods
         */
        fun voScore() = ReviewScore(2.5)
        fun voScore2() = ReviewScore(6.9)

        // --------------------------------------

        /**
         * Entity generation methods
         */
        fun entity(): Review {
            // need to save associated entities as foreign key
            val account = AccountTest.entity().save()
            val book = BookTest.entity().save()
            return Review(account, book, voScore())
        }

        fun entity2(): Review {
            // need to save associated entities as foreign key
            val account = AccountTest.entity2().save()
            val book = BookTest.entity2().save()
            return Review(account, book, voScore2())
        }
    }
}