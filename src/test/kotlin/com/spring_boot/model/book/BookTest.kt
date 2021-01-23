package com.spring_boot.model.book

import com.spring_boot.model.book.value_objects.BookCategory
import com.spring_boot.model.book.value_objects.BookScore
import com.spring_boot.model.book.value_objects.BookTitle
import com.spring_boot.model.book.value_objects.BookUrl

class BookTest {
    companion object {
        fun entity() =
                Book(BookTitle("test title"), BookCategory.SERVER_SIDE(), BookScore(2.5), BookUrl("https://test.com"))

        fun entity2() =
                Book(BookTitle("test title2"), BookCategory.FRONT_END(), BookScore(6.8), BookUrl("https://test2.com"))
    }
}