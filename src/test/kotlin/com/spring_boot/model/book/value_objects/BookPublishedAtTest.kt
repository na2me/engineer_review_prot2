package com.spring_boot.model.book.value_objects

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException
import java.time.LocalDate

class BookPublishedAtTest {
    companion object {
        /**
         * data provider methods for "testValidation"
         */
        @JvmStatic
        fun dataProvider() = listOf(
                // normal scenarios
                Arguments.of(LocalDate.now(), true),
                Arguments.of(LocalDate.of(2020, 1, 1), true),
                // all LocalDate should be accepted
        )
    }

    /**
     * validation test checking if provided parameters are valid for the VO
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: LocalDate, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookPublishedAt(value) }
            false -> assertThrows<ConstraintViolationException> { BookPublishedAt(value) }
        }
    }
}