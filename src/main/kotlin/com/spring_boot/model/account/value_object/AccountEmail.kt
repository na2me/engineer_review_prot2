package com.spring_boot.model.account.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotBlank
import org.valiktor.validate
import javax.persistence.Column

final class AccountEmail(
        @Column(name = "email", nullable = false, unique = true)
        override var value: String) : AbstractValueObject<String>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            validate(AccountEmail::value)
                    .isNotBlank()
                    .hasSize(min = 1, max = 100)
                    .isEmail()
        }
    }
}