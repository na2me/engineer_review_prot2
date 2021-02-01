package com.spring_boot.model.book

import com.spring_boot.model.book.value_object.*
import java.time.LocalDate

class BookTest {
    companion object {

        /**
         * Value Object generation methods
         */
        fun voTitle() = BookTitle("test title")
        fun voTitle2() = BookTitle("test title2")

        fun voCategory() = BookCategory(BookCategory.Categories.SERVER_SIDE)
        fun voCategory2() = BookCategory(BookCategory.Categories.FRONT_END)

        fun voScore() = BookScore(2.5)
        fun voScore2() = BookScore(6.8)

        fun voUrl() = BookUrl("https://test.com")
        fun voUrl2() = BookUrl("https://test2.com")

        fun voPublishedAt() = BookPublishedAt(LocalDate.now())
        fun voPublishedAt2() = BookPublishedAt(LocalDate.now().minusYears(1))

        // --------------------------------------

        /**
         * Entity generation methods
         */
        fun entity() =
                Book(voTitle(), voCategory(), voScore(), voUrl(), voPublishedAt())

        fun entity2() =
                Book(voTitle2(), voCategory2(), voScore2(), voUrl2(), voPublishedAt2())
    }
}