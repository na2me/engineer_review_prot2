package com.spring_boot.domain.review.repository

import com.spring_boot.base.util.Resolver
import com.spring_boot.domain.review.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
private interface ReviewRepositoryBase : JpaRepository<Review, Long>

class ReviewRepository {
    companion object {
        /**
         * resolve interface container and acquire that statistically
         */
        private fun repository(): ReviewRepositoryBase = Resolver.resolve(ReviewRepositoryBase::class.java)

        /**
         * save [entity]
         */
        fun save(entity: Review): Review {
            return repository().saveAndFlush(entity)
        }

        /**
         * find all entities
         */
        fun findAll(): List<Review> = repository().findAll()

        /**
         * find entity by [id]
         */
        fun findById(id: Long): Review = repository().findById(id).orElseThrow(::NoSuchElementException)

        /**
         * delete [entity]
         */
        fun delete(entity: Review) = repository().delete(entity)
    }
}