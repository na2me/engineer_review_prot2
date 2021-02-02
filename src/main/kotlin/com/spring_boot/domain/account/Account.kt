package com.spring_boot.domain.account

import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.account.factory.AccountFactory
import com.spring_boot.domain.account.value_object.AccountEmail
import com.spring_boot.domain.account.value_object.AccountId
import com.spring_boot.domain.account.value_object.AccountName
import com.spring_boot.domain.account.value_object.AccountPassword
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "account")
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

    companion object {
        /**
         * call `new` method process provided by Factory
         *
         * @return Account
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: AccountId = AccountId.getUnsaved()) =
                AccountFactory.new(params, isNew, id)
    }
}