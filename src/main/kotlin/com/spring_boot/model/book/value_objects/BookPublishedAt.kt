package com.spring_boot.model.book.value_objects

import com.google.gson.JsonArray
import com.spring_boot.base.model.value_object.AbstractValueObject
import org.json.JSONArray
import org.valiktor.validate
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class BookPublishedAt(
        @Column(name = "published_at", nullable = false)
        override var value: LocalDate) : AbstractValueObject<LocalDate>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            // all LocalDate value should be accepted
        }
    }

    /**
     * @return string formatted as LocalDate json
     */
    fun toJsonFormat(): JSONArray =
            JSONArray("[${value.year},${value.monthValue},${value.dayOfMonth}]")

}