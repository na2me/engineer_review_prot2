package com.spring_boot.domain.review.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.review.Review
import com.spring_boot.domain.review.repository.ReviewRepository
import com.spring_boot.domain.review.value_object.ReviewScore

class ReviewFactory {
//    companion object {
//        /**
//         * create or update instance by referring [params]
//         * if [isNew] is false, need [id] to specify the target entity
//         *
//         * @return Review
//         */
//        fun new(params: RequestParams, isNew: Boolean, id: Long): Review {
//            val accountId = params.getValue("accountId")
//            val bookId = params.getValue("bookId")
//            val score = ReviewScore(params.getValue("score").toDouble())
//
//
//            val entity: Review
//            when (isNew) {
//                // when the entity is newly created, prepare new entity
//                true -> {
//                    entity = Review(
//                            accountId,
//                            bookId,
//                            score
//                    )
//                }
//                // when the existed entity is updated, set each fields as new ones
//                false -> {
//                    entity = ReviewRepository.findById(id)
//                    entity.account = accountId
//                    entity.book = bookId
//                    entity.score = score
//                }
//            }
//            return ReviewRepository.save(entity)
//        }
//    }
}