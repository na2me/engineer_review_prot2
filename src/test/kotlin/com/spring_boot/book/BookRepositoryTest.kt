package com.spring_boot.book

import com.spring_boot.book.value_objects.BookTitle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testng.AssertJUnit.assertEquals
import org.testng.annotations.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest @Autowired constructor(
        val bookRepository: BookRepository) {

    @Test
    fun testFindByTitle() {
        val book = BookTest.entity()
        val saved = bookRepository.save(book)

        val found = bookRepository.findByTitle(BookTitle("test title"))
        assert(saved == found)
    }
}