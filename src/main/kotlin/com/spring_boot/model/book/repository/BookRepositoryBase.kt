package com.spring_boot.model.book.repository

import com.spring_boot.base.util.Resolver
import com.spring_boot.model.book.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
private interface BookRepositoryBase : JpaRepository<Book, Long>

/**
 * repository class which has static methods access to repository interface
 */
class BookRepository {
    companion object {
        /**
         * resolve interface container and acquire that statistically
         */
        private fun repository(): BookRepositoryBase = Resolver.resolve(BookRepositoryBase::class.java)

        /**
         * save [book] entity
         */
        fun save(book: Book): Book {
            return repository().saveAndFlush(book)
        }

        /**
         * find all book entities
         */
        fun findAll(): List<Book> = repository().findAll()

        /**
         * find book entity by [id]
         */
        fun findById(id: Long): Book = repository().findById(id).orElseThrow(::NoSuchElementException)

        /**
         * delete [book] entity
         */
        fun delete(book: Book) = repository().delete(book)
    }
}