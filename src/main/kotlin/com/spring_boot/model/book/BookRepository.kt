package com.spring_boot.model.book

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface BookRepository : CrudRepository<Book, Long>