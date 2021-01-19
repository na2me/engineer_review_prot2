package com.spring_boot.book

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface BookRepository : CrudRepository<Book, Long> {
    fun findByTitle(title: String): Book?
}