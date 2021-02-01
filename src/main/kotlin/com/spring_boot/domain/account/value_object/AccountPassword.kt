package com.spring_boot.domain.account.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotBlank
import org.valiktor.validate
import javax.persistence.Column

final class AccountPassword(
        @Column(name = "password", nullable = false)
        override var value: String) : AbstractValueObject<String>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            validate(AccountPassword::value)
                    .isNotBlank()
                    .hasSize(min = 1, max = 100)
        }
    }
}