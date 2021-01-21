package com.spring_boot.book

import com.spring_boot.book.value_objects.BookTitle
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface BookRepository : CrudRepository<Book, Long> {
    fun findByTitle(title: BookTitle): Book?
}