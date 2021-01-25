package com.spring_boot.model.book

import com.spring_boot.model.book.value_objects.*

class BookTest {
    companion object {
        fun entity() =
                Book(BookTitle("test title"), BookCategory(Categories.SERVER_SIDE), BookScore(2.5), BookUrl("https://test.com"))

        fun entity2() =
                Book(BookTitle("test title2"), BookCategory(Categories.FRONT_END), BookScore(6.8), BookUrl("https://test2.com"))
    }
}