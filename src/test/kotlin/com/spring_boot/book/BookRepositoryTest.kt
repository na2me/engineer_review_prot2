package com.spring_boot.book

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest @Autowired constructor(
        val bookRepository: BookRepository) {

    @Test
    fun testFindByTitle() {
        val book = Book("test title", 1, 2.0, "https://test.com")
        bookRepository.save(book)

        val foundBook = bookRepository.findByTitle("test title")
        assertThat(foundBook).isEqualTo(book)
    }
}