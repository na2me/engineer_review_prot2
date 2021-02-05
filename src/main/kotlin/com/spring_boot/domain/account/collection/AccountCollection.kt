package com.spring_boot.domain.account.collection

import com.spring_boot.base.collection.AbstractEntityCollection
import com.spring_boot.domain.account.Account

/**
 * @return [AccountCollection] converted from [List<Book>]
 */
fun List<Account>.toCollection(): AccountCollection {
    return AccountCollection(this)
}

class AccountCollection(list: List<Account>) : AbstractEntityCollection<AccountCollection, Account>(list) {
}