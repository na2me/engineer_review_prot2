package com.spring_boot.book.value_objects

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class BookCategory(
        @NotNull
        @Size(min = 1, max = 8)
        val value: Int) {

    fun isEqual(other: BookCategory): Boolean =
            this.value == other.value

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