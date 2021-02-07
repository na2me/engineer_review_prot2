package com.spring_boot.domain.book.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import org.valiktor.functions.hasSize
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class BookDescription(
        @Column(name = "description", nullable = false)
        override var value: String) : AbstractValueObject<String>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            validate(BookDescription::value).hasSize(min = 0, max = 500)
        }
    }
}