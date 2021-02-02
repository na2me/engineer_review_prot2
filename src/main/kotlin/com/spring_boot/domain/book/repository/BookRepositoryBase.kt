package com.spring_boot.domain.book.repository

import com.spring_boot.base.util.Resolver
import com.spring_boot.domain.book.Book
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
         * save [entity]
         */
        fun save(entity: Book): Book {
            return repository().saveAndFlush(entity)
        }

        /**
         * find all entities
         */
        fun findAll(): List<Book> = repository().findAll()

        /**
         * find entity by [id]
         */
        fun findById(id: Long): Book = repository().findById(id).orElseThrow(::NoSuchElementException)

        /**
         * delete [entity]
         */
        fun delete(entity: Book) = repository().delete(entity)
    }
}