package com.spring_boot.domain.account.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.base.util.security.passwordEncoder
import com.spring_boot.domain.account.Account
import com.spring_boot.domain.account.repository.AccountRepository
import com.spring_boot.domain.account.value_object.AccountEmail
import com.spring_boot.domain.account.value_object.AccountId
import com.spring_boot.domain.account.value_object.AccountName
import com.spring_boot.domain.account.value_object.AccountPassword

class AccountFactory {
    companion object {
        /**
         * create or update instance by referring [params]
         * if [isNew] is false, need [id] to specify the target entity
         *
         * @return [Account]
         */
        fun new(params: RequestParams, isNew: Boolean, id: AccountId): Account {
            val name = AccountName(params.getValue("name"))
            val email = AccountEmail(params.getValue("email"))
            // encode raw password to record
            val encodedPassword = passwordEncoder().encode(params.getValue("password"))
            val password = AccountPassword(encodedPassword)

            val entity: Account
            when (isNew) {
                // when the entity is newly created, prepare new entity
                true -> {
                    entity = Account(
                            name,
                            email,
                            password
                    )
                }
                // when the existed entity is updated, set each fields with new ones
                false -> {
                    entity = AccountRepository.findById(id)
                    entity.name = name
                    entity.email = email
                    entity.password = password
                }
            }
            return entity.save()
        }
    }
}