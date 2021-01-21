package com.spring_boot.book.value_objects

import com.google.gson.Gson
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Embeddable
class BookTitle(
        @NotNull
        @Size(min = 1, max = 50)
        @NotBlank
        val value: String) {


        fun isEqual(other: BookTitle): Boolean =
                this.value == other.value

        override fun toString(): String = Gson().toJson(this)
}