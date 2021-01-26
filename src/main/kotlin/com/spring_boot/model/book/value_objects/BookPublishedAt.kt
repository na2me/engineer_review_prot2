package com.spring_boot.model.book.value_objects

import com.spring_boot.base.model.value_object.AbstractValueObject
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class BookPublishedAt(
        @Column(name = "published_at", nullable = false)
        override var value: LocalDate) : AbstractValueObject<LocalDate>() {

    fun toJsonFormat(): String {
        return "[${value.year},${value.monthValue},${value.dayOfMonth}]"
    }
}