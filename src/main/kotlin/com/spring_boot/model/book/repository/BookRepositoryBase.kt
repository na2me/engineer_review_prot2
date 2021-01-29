package com.spring_boot.model.book.repository

import com.spring_boot.base.util.resolve
import com.spring_boot.model.book.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
private interface BookRepositoryBase : JpaRepository<Book, Long>

class BookRepository {
    companion object {
        private fun repository(): BookRepositoryBase = resolve(BookRepositoryBase::class.java)

        fun count(): Long {
            return repository().count()
        }

        fun save(book: Book): Book {
            return repository().saveAndFlush(book)
        }

        fun findAll() = repository().findAll()

        fun findById(id: Long): Book = repository().findById(id).orElseThrow(::NoSuchElementException)

        fun delete(book: Book) = repository().delete(book)
    }
}