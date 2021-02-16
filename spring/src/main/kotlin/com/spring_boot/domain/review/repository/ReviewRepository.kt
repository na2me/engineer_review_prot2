package com.spring_boot.domain.review.repository

import com.spring_boot.base.util.Resolver
import com.spring_boot.domain.book.value_object.BookId
import com.spring_boot.domain.review.Review
import com.spring_boot.domain.review.collection.ReviewCollection
import com.spring_boot.domain.review.collection.toCollection
import com.spring_boot.domain.review.value_object.ReviewId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
private interface ReviewRepositoryBase : JpaRepository<Review, Long> {
    fun findAllByBookId(bookId: Long): MutableList<Review>
}

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
        fun findAll(): ReviewCollection = repository().findAll().toCollection()

        /**
         * find entity by [id]
         */
        fun findById(id: ReviewId): Review =
                repository().findById(id.value).orElseThrow(::NoSuchElementException)

        /**
         * find entity by [id]
         */
        fun findAllByBookId(id: BookId): ReviewCollection =
                repository().findAllByBookId(id.value).toCollection()

        /**
         * delete [entity]
         */
        fun delete(entity: Review) = repository().delete(entity)
    }
}