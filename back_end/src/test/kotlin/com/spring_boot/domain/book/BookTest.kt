package com.spring_boot.domain.book

import com.spring_boot.base.AbstractEntityTest
import com.spring_boot.domain.author.AuthorTest
import com.spring_boot.domain.book.value_object.*
import java.time.LocalDate

class BookTest : AbstractEntityTest() {
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

        fun voDescription() = BookDescription("This is test description 1")
        fun voDescription2() = BookDescription("This is test description 2")

        fun voPublishedAt() = BookPublishedAt(LocalDate.now())
        fun voPublishedAt2() = BookPublishedAt(LocalDate.now().minusYears(1))

        // --------------------------------------

        /**
         * Entity generation methods
         */
        fun entity(): Book {
            val author = AuthorTest.entity().save()
            return Book(author, voTitle(), voCategory(), voScore(), voUrl(), voDescription(), voPublishedAt())
        }

        fun entity2(): Book {
            val author2 = AuthorTest.entity2().save()
            return Book(author2, voTitle2(), voCategory2(), voScore2(), voUrl2(), voDescription(), voPublishedAt2())
        }
    }
}