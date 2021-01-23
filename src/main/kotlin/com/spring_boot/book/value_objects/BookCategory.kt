package com.spring_boot.book.value_objects

import com.google.gson.Gson
import org.valiktor.functions.hasSize
import org.valiktor.functions.isBetween
import org.valiktor.validate
import javax.persistence.Embeddable

@Embeddable
final class BookCategory(val value: Int) {

    init {
        validate(this) {
            validate(BookCategory::value).isBetween(start = 1, end = 8)
        }
    }


    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is BookCategory) return false
        return value == other.value
    }

    override fun hashCode() = value.hashCode()

    override fun toString(): String = Gson().toJson(this)

    companion object {
        fun SERVER_SIDE(): BookCategory {
            return BookCategory(1)
        }

        fun FRONT_SIDE(): BookCategory {
            return BookCategory(2)
        }

        fun INFRASTORUCTURE(): BookCategory {
            return BookCategory(3)
        }

        fun OS(): BookCategory {
            return BookCategory(4)
        }

        fun HARDWARE(): BookCategory {
            return BookCategory(5)
        }

        fun DESIGN(): BookCategory {
            return BookCategory(6)
        }

        fun NETWORK(): BookCategory {
            return BookCategory(7)
        }

        fun DATA_SCIENCE(): BookCategory {
            return BookCategory(8)
        }
    }
}