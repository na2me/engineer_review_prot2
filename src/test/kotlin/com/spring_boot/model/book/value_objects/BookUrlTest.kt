package com.spring_boot.model.book.value_objects

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class BookUrlTest {
    companion object {
        @JvmStatic
        fun dataProvider() = listOf(
                // normal scenarios
                Arguments.of("https://test.com", true),
                Arguments.of("https://www.amazon.co.jp/%E3%82%A8%E3%83%AA%E3%83%83%E3%82%AF%E3%83%BB%E3%82%A8%E3%83%B4%E3%82%A1%E3%83%B3%E3%82%B9%E3%81%AE%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88-Eric-Evans-ebook/dp/B00GRKD6XU/ref=sr_1_2?__mk_ja_JP=%E3%82%AB%E3%82%BF%E3%82%AB%E3%83%8A&dchild=1&keywords=%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88&qid=1611382696&sr=8-2", true),
                // exceptional scenarios
                Arguments.of("", false),
                Arguments.of("this should be rejected", false)
        )
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: String, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookUrl(value) }
            false -> assertThrows<ConstraintViolationException> { BookUrl(value) }
        }
    }
}