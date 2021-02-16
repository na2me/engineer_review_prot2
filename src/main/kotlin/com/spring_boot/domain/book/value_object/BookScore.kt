package com.spring_boot.domain.book.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import org.valiktor.functions.isBetween
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class BookScore(
        @Column(name = "score", nullable = false)
        override var value: Double) : AbstractValueObject<Double>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            validate(BookScore::value).isBetween(start = 0.0, end = 10.0)
        }
    }
}