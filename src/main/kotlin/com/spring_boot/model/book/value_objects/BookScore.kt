package com.spring_boot.model.book.value_objects

import com.spring_boot.base.model.value_object.AbstractValueObjectDouble
import org.valiktor.functions.isBetween
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class BookScore(
        @Column(name = "score", nullable = false)
        override var value: Double) : AbstractValueObjectDouble() {

    init {
        validate(this) {
            validate(BookScore::value).isBetween(start = 0.0, end = 10.0)
        }
    }
}