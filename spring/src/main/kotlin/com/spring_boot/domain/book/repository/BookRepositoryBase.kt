package com.spring_boot.domain.book.repository

import com.spring_boot.base.util.Resolver
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.collection.BookCollection
import com.spring_boot.domain.book.collection.toCollection
import com.spring_boot.domain.book.value_object.BookId
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
        fun findAll(): BookCollection = repository().findAll().toCollection()

        /**
         * find entity by [id]
         */
        fun findById(id: BookId): Book =
                repository().findById(id.value).orElseThrow(::NoSuchElementException)


        /**
         * delete [entity]
         */
        fun delete(entity: Book) = repository().delete(entity)
    }
}