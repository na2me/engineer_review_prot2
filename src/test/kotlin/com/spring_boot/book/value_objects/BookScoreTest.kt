package com.spring_boot.book.value_objects

import com.spring_boot.model.book.value_objects.BookScore
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class BookScoreTest {
    companion object {
        @JvmStatic
        fun dataProvider() = listOf(
                // normal scenarios
                Arguments.of(0.0, true),
                Arguments.of(5, true),
                Arguments.of(10.0, true),
                // exceptional scenarios
                Arguments.of(-0.1, false),
                Arguments.of(10.1, false)
        )
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: Double, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookScore(value) }
            false -> assertThrows<ConstraintViolationException> { BookScore(value) }
        }
    }
}