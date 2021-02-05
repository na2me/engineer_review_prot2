package com.spring_boot.domain.author.repository

import com.spring_boot.base.util.Resolver
import com.spring_boot.domain.author.Author
import com.spring_boot.domain.author.collection.AuthorCollection
import com.spring_boot.domain.author.collection.toCollection
import com.spring_boot.domain.author.value_object.AuthorId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
private interface AuthorRepositoryBase : JpaRepository<Author, Long>

/**
 * repository class which has static methods access to repository interface
 */
class AuthorRepository {
    companion object {
        /**
         * resolve interface container and acquire that statistically
         */
        private fun repository(): AuthorRepositoryBase = Resolver.resolve(AuthorRepositoryBase::class.java)

        /**
         * save [entity]
         */
        fun save(entity: Author): Author {
            return repository().saveAndFlush(entity)
        }

        /**
         * find all entities
         */
        fun findAll(): AuthorCollection = repository().findAll().toCollection()

        /**
         * find entity by [id]
         */
        fun findById(id: AuthorId): Author =
                repository().findById(id.value).orElseThrow(::NoSuchElementException)


        /**
         * delete [entity]
         */
        fun delete(entity: Author) = repository().delete(entity)
    }
}