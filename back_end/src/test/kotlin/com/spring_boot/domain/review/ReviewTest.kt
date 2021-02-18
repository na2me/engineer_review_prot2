package com.spring_boot.domain.review

import com.spring_boot.base.AbstractEntityTest
import com.spring_boot.domain.account.AccountTest
import com.spring_boot.domain.book.BookTest
import com.spring_boot.domain.book.repository.BookRepository
import com.spring_boot.domain.review.value_object.ReviewRating
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReviewTest : AbstractEntityTest() {

    @Test
    fun testReCalculateBookRating() {
        // save two reviews both of which refer the same Book
        val review1 = entity().save()
        val review2 = entity2()
        review2.book = review1.book
        review2.save()

        // Book's rating should be average of two reviews above
        val averageRating = (review1.rating.value + review2.rating.value) / 2
        val fetchedBook = BookRepository.findById(review1.book.id())
        assertEquals(averageRating, fetchedBook.rating.value)
    }

    companion object {
        /**
         * Value Object generation methods
         */
        fun voRating() = ReviewRating(2.5)
        fun voRating2() = ReviewRating(6.9)

        // --------------------------------------

        /**
         * Entity generation methods
         */
        fun entity(): Review {
            // need to save associated entities as foreign key
            val account = AccountTest.entity().save()
            val book = BookTest.entity().save()
            return Review(account, book, voRating())
        }

        fun entity2(): Review {
            // need to save associated entities as foreign key
            val account = AccountTest.entity2().save()
            val book = BookTest.entity2().save()
            return Review(account, book, voRating2())
        }
    }
}