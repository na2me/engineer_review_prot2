package com.spring_boot.book.value_objects

import com.google.gson.Gson
import org.valiktor.functions.hasSize
import org.valiktor.validate
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Embeddable
class BookTitle(val value: String) {

        init {
            validate(this) {
                    validate(BookTitle::value).hasSize(min = 1, max = 50)
            }
        }


        fun isEqual(other: BookTitle): Boolean =
                this.value == other.value

        override fun toString(): String = Gson().toJson(this)
}