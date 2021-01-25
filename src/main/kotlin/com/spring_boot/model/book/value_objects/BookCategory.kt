package com.spring_boot.model.book.value_objects

import com.spring_boot.base.model.value_object.AbstractValueObject
import javax.persistence.Column
import javax.persistence.Embeddable


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

@Embeddable
final class BookCategory(
        @Column(name = "category", nullable = false)
        override var value: Categories) : AbstractValueObject<Categories>()