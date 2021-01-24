package com.spring_boot.model.book.value_objects

import com.spring_boot.base.model.value_object.AbstractValueObjectString
import org.valiktor.functions.hasSize
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class BookTitle(
        @Column(name = "title", nullable = false)
        override var value: String) : AbstractValueObjectString() {

    init {
        validate(this) {
            validate(BookTitle::value).hasSize(min = 1, max = 50)
        }
    }
}