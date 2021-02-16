package com.spring_boot.domain.account

import com.spring_boot.base.AbstractEntityTest
import com.spring_boot.domain.account.value_object.AccountEmail
import com.spring_boot.domain.account.value_object.AccountName
import com.spring_boot.domain.account.value_object.AccountPassword

class AccountTest : AbstractEntityTest() {
    companion object {
        /**
         * Value Object generation methods
         */
        fun voName() = AccountName("test account1")
        fun voName2() = AccountName("test account2")

        fun voEmail() = AccountEmail("test1@test.com")
        fun voEmail2() = AccountEmail("test2@test.com")

        fun voPassword() = AccountPassword("thisismypassword1")
        fun voPassword2() = AccountPassword("thisismypassword2")

        // --------------------------------------

        /**
         * Entity generation methods
         */
        fun entity() = Account(voName(), voEmail(), voPassword())

        fun entity2() = Account(voName2(), voEmail2(), voPassword2())
    }
}