package com.spring_boot.domain.review.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.account.value_object.AccountId
import com.spring_boot.domain.book.value_object.BookId
import com.spring_boot.domain.review.Review
import com.spring_boot.domain.review.repository.ReviewRepository
import com.spring_boot.domain.review.value_object.ReviewId
import com.spring_boot.domain.review.value_object.ReviewRating

class ReviewFactory {
    companion object {
        /**
         * create or update instance by referring [params]
         * if [isNew] is false, need [id] to specify the target entity
         *
         * @return [Review]
         */
        fun new(params: RequestParams, isNew: Boolean, id: ReviewId): Review {
            val account = AccountId(params.getValue("accountId").toLong()).toEntity()
            val book = BookId(params.getValue("bookId").toLong()).toEntity()
            val rating = ReviewRating(params.getValue("rating").toDouble())

            val entity: Review
            when (isNew) {
                // when the entity is newly created, prepare new entity
                true -> {
                    entity = Review(
                            account,
                            book,
                            rating
                    )
                }
                // when the existed entity is updated, set each fields with new ones
                false -> {
                    entity = ReviewRepository.findById(id)
                    entity.account = account
                    entity.book = book
                    entity.rating = rating
                }
            }
            return entity.save()
        }
    }
}