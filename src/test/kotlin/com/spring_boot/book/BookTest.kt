package com.spring_boot.book

import com.spring_boot.book.value_objects.BookTitle

class BookTest {
    companion object {
        fun entity() =
                Book(BookTitle("test title"), 1, 2.0, "https://test.com")

        fun entity2() =
                Book(BookTitle("test title2"), 2, 4.0, "https://test2.com")
    }
}