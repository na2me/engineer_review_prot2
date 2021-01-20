package com.spring_boot.book.value_objects

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull
import javax.xml.validation.Validator

@Embeddable
class BookTitle(
        @NotNull
        @Size(min = 1, max = 50)
         val title: String) {
}