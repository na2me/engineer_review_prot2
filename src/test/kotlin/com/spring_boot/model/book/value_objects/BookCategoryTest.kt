package com.spring_boot.model.book.value_objects

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class BookCategoryTest {
    companion object {
        @JvmStatic
        fun dataProvider() = listOf(
                // normal scenarios
                Arguments.of(Categories.SERVER_SIDE, true),
                Arguments.of(Categories.FRONT_END, true),
        )
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: Categories, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookCategory(value) }
            false -> assertThrows<ConstraintViolationException> { BookCategory(value) }
        }
    }
}