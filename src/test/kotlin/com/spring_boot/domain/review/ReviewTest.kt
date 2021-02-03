package com.spring_boot.domain.review

import com.spring_boot.domain.account.AccountTest
import com.spring_boot.domain.book.BookTest
import com.spring_boot.domain.review.value_object.ReviewScore

class ReviewTest {
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