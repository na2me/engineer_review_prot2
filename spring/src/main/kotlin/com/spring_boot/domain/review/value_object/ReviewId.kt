package com.spring_boot.domain.review.value_object

import com.spring_boot.base.model.entity.UNSAVED_VALUE
import com.spring_boot.base.model.value_object.AbstractValueObjectId
import com.spring_boot.domain.review.Review
import com.spring_boot.domain.review.repository.ReviewRepository
import javax.persistence.Embeddable

@Embeddable
data class ReviewId(override var value: Long) : AbstractValueObjectId() {
    /**
     * @return entity which has the same ID as this ReviewId instance
     */
    override fun toEntity(): Review = ReviewRepository.findById(this)

    companion object {
        /**
         * @return self instance which has UNSAVED_VALUE as its value
         */
        fun getUnsavedId() = ReviewId(UNSAVED_VALUE)
    }
}