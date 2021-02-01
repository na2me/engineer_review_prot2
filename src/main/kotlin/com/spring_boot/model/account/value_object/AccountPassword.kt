package com.spring_boot.model.account.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import org.valiktor.functions.doesNotContain
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotBlank
import org.valiktor.validate
import javax.persistence.Column

//TODO:make password as encryption
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
                    .doesNotContain(" ")
                    .doesNotContain("<")
                    .doesNotContain(">")
                    .doesNotContain("*")
                    .doesNotContain("/")
                    .doesNotContain("^")
                    .doesNotContain("$")
                    .hasSize(min = 12, max = 30)
        }
    }
}