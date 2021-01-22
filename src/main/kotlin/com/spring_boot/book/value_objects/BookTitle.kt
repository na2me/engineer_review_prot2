package com.spring_boot.book.value_objects

import com.google.gson.Gson
import org.valiktor.functions.hasSize
import org.valiktor.validate
import javax.persistence.Embeddable

@Embeddable
class BookTitle(val value: String) {

    init {
        validate(this) {
            validate(BookTitle::value).hasSize(min = 1, max = 50)
        }
    }


    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is BookTitle) return false
        return value == other.value
    }


    override fun toString(): String = Gson().toJson(this)

    override fun hashCode(): Int {
        return value.hashCode()
    }
}