package com.spring_boot.domain.account

import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.account.factory.AccountFactory
import com.spring_boot.domain.account.repository.AccountRepository
import com.spring_boot.domain.account.value_object.AccountEmail
import com.spring_boot.domain.account.value_object.AccountId
import com.spring_boot.domain.account.value_object.AccountName
import com.spring_boot.domain.account.value_object.AccountPassword
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "accounts")
class Account(
        @ApiModelProperty(value = "Name", required = true)
        @Embedded
        var name: AccountName,
        @ApiModelProperty(value = "Email", required = true)
        @Embedded
        var email: AccountEmail,
        @ApiModelProperty(value = "Password", required = true)
        @Embedded
        var password: AccountPassword) : AbstractEntity<AccountId>() {

    /**
     * @return Value Object ID
     */
    override fun id() = AccountId(this.id)

    /**
     * @return saved entity
     */
    override fun save() = AccountRepository.save(this)

    companion object {
        /**
         * call `new` method process provided by Factory
         *
         * @return Account
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: AccountId = AccountId.getUnsavedId()) =
                AccountFactory.new(params, isNew, id)
    }
}