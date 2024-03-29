package com.spring_boot.domain.author.value_object

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class AuthorBiographyTest {
    companion object {
        /**
         * data provider methods for "testValidation"
         */
        @JvmStatic
        fun dataProvider() = listOf(
                // normal cases
                Arguments.of("test biography", true),
                Arguments.of("a".repeat(300), true),
                // exceptional cases
                Arguments.of("", false),
                Arguments.of("a".repeat(301), false)
        )
    }

    /**
     * validation test checking if provided parameters are valid for the VO
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: String, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { AuthorBiography(value) }
            false -> assertThrows<ConstraintViolationException> { AuthorBiography(value) }
        }
    }
}