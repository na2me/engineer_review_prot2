package com.spring_boot.domain.account.value_object

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.valiktor.ConstraintViolationException

class AccountPasswordTest {
    companion object {
        /**
         * data provider methods for "testValidation"
         */
        @JvmStatic
        fun dataProvider() = listOf(
                // normal cases
                Arguments.of("ThisIsSamplePassWord", true),
                Arguments.of("a".repeat(100), true),
                // exceptional cases
                Arguments.of("", false),
                Arguments.of("a".repeat(101), false),
        )
    }

    /**
     * validation test checking if provided parameters are valid for the VO
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testValidation(value: String, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { AccountPassword(value) }
            false -> assertThrows<ConstraintViolationException> { AccountPassword(value) }
        }
    }
}