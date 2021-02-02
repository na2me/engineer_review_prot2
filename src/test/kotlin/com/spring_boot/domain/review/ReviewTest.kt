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
        fun entity() =
                Review(AccountTest.entity(), BookTest.entity(), voScore())

        fun entity2() =
                Review(AccountTest.entity2(), BookTest.entity2(), voScore2())
    }
}