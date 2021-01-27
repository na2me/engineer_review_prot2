package com.spring_boot.model.book.repository

import com.spring_boot.model.book.Book
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<Book, Long>