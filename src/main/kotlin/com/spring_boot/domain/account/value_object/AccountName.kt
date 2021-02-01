package com.spring_boot.domain.account.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotBlank
import org.valiktor.validate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
final class AccountName(
        @Column(name = "name", nullable = false, unique = true)
        override var value: String) : AbstractValueObject<String>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            validate(AccountName::value)
                    .isNotBlank()
                    .hasSize(min = 1, max = 30)
        }
    }
}