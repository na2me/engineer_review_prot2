package com.spring_boot.model.book.value_objects

import com.spring_boot.model.book.value_objects.BookCategory
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
                Arguments.of(1, true),
                Arguments.of(8, true),
                // exceptional scenarios
                Arguments.of(0, false),
                Arguments.of(9, false),
        )
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: Int, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookCategory(value) }
            false -> assertThrows<ConstraintViolationException> { BookCategory(value) }
        }
    }
}