package com.spring_boot.domain.book.value_object

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class BookCategoryTest {
    companion object {
        /**
         * data provider methods for "testValidation"
         */
        @JvmStatic
        fun dataProvider() = listOf(
                // normal cases
                Arguments.of(BookCategory.Categories.SERVER_SIDE, true),
                Arguments.of(BookCategory.Categories.FRONT_END, true),
        )
    }

    /**
     * validation test checking if provided parameters are valid for the VO
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: BookCategory.Categories, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookCategory(value) }
            false -> assertThrows<ConstraintViolationException> { BookCategory(value) }
        }
    }
}