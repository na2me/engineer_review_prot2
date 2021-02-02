package com.spring_boot.domain.account.value_object

import com.spring_boot.base.model.value_object.AbstractValueObjectId
import com.spring_boot.domain.account.Account
import com.spring_boot.domain.account.repository.AccountRepository

class AccountId(override var value: Long) : AbstractValueObjectId() {
    /**
     * @return entity which has the same ID as this ReviewId instance
     */
    override fun toEntity(): Account = AccountRepository.findById(this)
}