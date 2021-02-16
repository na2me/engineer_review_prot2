package com.spring_boot.domain.author

import com.spring_boot.base.AbstractEntityTest
import com.spring_boot.domain.author.value_object.AuthorBiography
import com.spring_boot.domain.author.value_object.AuthorName

class AuthorTest : AbstractEntityTest() {
    companion object {
        /**
         * Value Object generation methods
         */
        fun voName() = AuthorName("test name")
        fun voName2() = AuthorName("test name2")

        fun voBiography() = AuthorBiography("This is a short summary of an author's history")
        fun voBiography2() = AuthorBiography("2:This is a short summary of an author's history")

        // --------------------------------------

        /**
         * Entity generation methods
         */
        fun entity() = Author(voName(), voBiography())
        fun entity2() = Author(voName2(), voBiography2())
    }
}