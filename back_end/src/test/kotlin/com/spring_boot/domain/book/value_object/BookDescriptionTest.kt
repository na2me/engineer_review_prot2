package com.spring_boot.domain.book.value_object

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class BookDescriptionTest {
    companion object {
        /**
         * data provider methods for "testValidation"
         */
        @JvmStatic
        fun dataProvider() = listOf(
                // normal cases
                Arguments.of("test description", true),
                Arguments.of("", true),
                Arguments.of("a".repeat(500), true),
                // exceptional cases
                Arguments.of("a".repeat(501), false)
        )
    }

    /**
     * validation test checking if provided parameters are valid for the VO
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: String, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookDescription(value) }
            false -> assertThrows<ConstraintViolationException> { BookDescription(value) }
        }
    }
}