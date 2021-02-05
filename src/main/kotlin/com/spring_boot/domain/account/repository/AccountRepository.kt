package com.spring_boot.domain.account.repository

import com.spring_boot.base.util.Resolver
import com.spring_boot.domain.account.Account
import com.spring_boot.domain.account.collection.AccountCollection
import com.spring_boot.domain.account.collection.toCollection
import com.spring_boot.domain.account.value_object.AccountId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
private interface AccountRepositoryBase : JpaRepository<Account, Long>

/**
 * repository class which has static methods access to repository interface
 */
class AccountRepository {
    companion object {
        /**
         * resolve interface container and acquire that statistically
         */
        private fun repository(): AccountRepositoryBase = Resolver.resolve(AccountRepositoryBase::class.java)

        /**
         * save [account] entity
         */
        fun save(account: Account): Account {
            return repository().saveAndFlush(account)
        }

        /**
         * find all book entities
         */
        fun findAll(): AccountCollection = repository().findAll().toCollection()

        /**
         * find book entity by [id]
         */
        fun findById(id: AccountId): Account =
                repository().findById(id.value).orElseThrow(::NoSuchElementException)


        /**
         * delete [account] entity
         */
        fun delete(account: Account) = repository().delete(account)
    }
}