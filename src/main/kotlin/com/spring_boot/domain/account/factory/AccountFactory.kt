package com.spring_boot.domain.account.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.account.Account
import com.spring_boot.domain.account.repository.AccountRepository
import com.spring_boot.domain.account.value_object.AccountEmail
import com.spring_boot.domain.account.value_object.AccountName
import com.spring_boot.domain.account.value_object.AccountPassword

class AccountFactory {
    companion object {
        /**
         * create or update instance by referring [params]
         * if [isNew] is false, need [id] to specify the target entity
         *
         * @return Account
         */
        fun new(params: RequestParams, isNew: Boolean, id: Long): Account {
            val name = AccountName(params.getValue("name"))
            val email = AccountEmail(params.getValue("email"))
            val password = AccountPassword(params.getValue("password"))

            val account: Account
            when (isNew) {
                // when the entity is newly created, prepare new entity
                true -> {
                    account = Account(
                            name,
                            email,
                            password
                    )
                }
                // when the existed entity is updated, set each fields as new ones
                false -> {
                    account = AccountRepository.findById(id)
                    account.name = name
                    account.email = email
                    account.password = password
                }
            }
            return AccountRepository.save(account)
        }
    }
}