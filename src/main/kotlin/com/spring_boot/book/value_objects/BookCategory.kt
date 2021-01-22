package com.spring_boot.book.value_objects

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class BookCategory(
        @NotNull
        @Size(min = 1, max = 8)
        val value: Int) {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is BookCategory) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

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