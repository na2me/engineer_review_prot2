package com.spring_boot.domain.account.value_object

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class AccountEmailTest {
    companion object {
        /**
         * data provider methods for "testValidation"
         */
        @JvmStatic
        fun dataProvider() = listOf(
                // normal cases
                Arguments.of("test@gmail.com", true),
                Arguments.of("${"a".repeat(88)}@ezweb.ne.jp", true),
                // exceptional cases
                Arguments.of("this should be rejected", false),
                Arguments.of("${"a".repeat(89)}@ezweb.ne.jp", false)
        )
    }

    /**
     * validation test checking if provided parameters are valid for the VO
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: String, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { AccountEmail(value) }
            false -> assertThrows<ConstraintViolationException> { AccountEmail(value) }
        }
    }
}