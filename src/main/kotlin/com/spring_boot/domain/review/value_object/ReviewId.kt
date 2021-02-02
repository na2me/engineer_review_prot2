package com.spring_boot.domain.review.value_object

import com.spring_boot.base.model.value_object.AbstractValueObjectId
import com.spring_boot.domain.review.Review
import com.spring_boot.domain.review.repository.ReviewRepository
import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
final class ReviewId(override var value: Long) : AbstractValueObjectId(), Serializable {
    /**
     * @return entity which has the same ID as this ReviewId instance
     */
    override fun toEntity(): Review = ReviewRepository.findById(this)
}