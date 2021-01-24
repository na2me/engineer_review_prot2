package com.spring_boot.model.book.value_objects

import com.spring_boot.base.AbstractValueObjectString
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isWebsite
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class BookUrl(
        @Column(name = "url", nullable = false)
        override var value: String) : AbstractValueObjectString() {

    init {
        validate(this) {
            validate(BookUrl::value).isNotBlank().isWebsite()
        }
    }
}