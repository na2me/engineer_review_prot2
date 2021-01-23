package com.spring_boot.model.book.value_objects

import com.google.gson.Gson
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isWebsite
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class BookUrl(
        @Column(name = "url", nullable = false)
        val value: String) {

    init {
        validate(this) {
            validate(BookUrl::value).isNotBlank().isWebsite()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is BookUrl) return false
        return value == other.value
    }

    override fun hashCode() = value.hashCode()

    override fun toString(): String = Gson().toJson(this)
}