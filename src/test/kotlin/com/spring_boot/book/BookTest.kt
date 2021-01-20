package com.spring_boot.book

class BookTest {
    companion object {
        fun entity() =
                Book("test title", 1, 2.0, "https://test.com")

        fun entity2() =
                Book("test title2", 2, 4.0, "https://test2.com")
    }
}