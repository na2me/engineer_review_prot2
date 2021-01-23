package com.spring_boot.model.book.value_objects

import com.google.gson.Gson
import org.valiktor.functions.isBetween
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class BookScore(
        @Column(name = "score", nullable = false)
        val value: Double) {

    init {
        validate(this) {
            validate(BookScore::value).isBetween(start = 0.0, end = 10.0)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is BookScore) return false
        return value == other.value
    }

    override fun hashCode() = value.hashCode()

    override fun toString(): String = Gson().toJson(this)
}