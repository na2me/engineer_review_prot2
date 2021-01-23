package com.spring_boot.model.book.value_objects

import com.spring_boot.model.book.value_objects.BookTitle
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class BookTitleTest {
    companion object {
        @JvmStatic
        fun dataProvider() = listOf(
                // normal scenarios
                Arguments.of("test title", true),
                Arguments.of("a", true),
                Arguments.of("a".repeat(50), true),
                // exceptional scenarios
                Arguments.of("", false),
                Arguments.of("a".repeat(51), false)
        )
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: String, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookTitle(value) }
            false -> assertThrows<ConstraintViolationException> { BookTitle(value) }
        }
    }
}

