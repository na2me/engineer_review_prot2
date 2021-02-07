package com.spring_boot.domain.book.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import javax.persistence.Column
import javax.persistence.Embeddable


@Embeddable
data class BookCategory(
        @Column(name = "category", nullable = false)
        override var value: Categories) : AbstractValueObject<BookCategory.Categories>() {

    enum class Categories {
        SERVER_SIDE,
        FRONT_END,
        INFRASTRUCTURE,
        OS,
        HARDWARE,
        DESIGN,
        NETWORK,
        DATA_SCIENCE
    }

}