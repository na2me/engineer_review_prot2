package com.spring_boot.domain.review.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import org.valiktor.functions.isBetween
import org.valiktor.validate
import javax.persistence.Column

final class ReviewScore(
        @Column(name = "score", nullable = false)
        override var value: Double)  : AbstractValueObject<Double>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            validate(ReviewScore::value).isBetween(start = 0.0, end = 10.0)
        }
    }
}