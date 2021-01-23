package com.spring_boot.book

import com.spring_boot.model.book.Book
import com.spring_boot.model.book.value_objects.BookCategory
import com.spring_boot.model.book.value_objects.BookScore
import com.spring_boot.model.book.value_objects.BookTitle

class BookTest {
    companion object {
        fun entity() =
                Book(BookTitle("test title"), BookCategory.SERVER_SIDE(), BookScore(2.5), "https://test.com")

        fun entity2() =
                Book(BookTitle("test title2"), BookCategory.FRONT_END(), BookScore(6.8), "https://test2.com")
    }
}