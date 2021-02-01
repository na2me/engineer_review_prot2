package com.spring_boot.model.account.controller

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.model.account.Account
import com.spring_boot.model.account.repository.AccountRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/account/")
@Api(description = "Account Controller")
class AccountController {

    @ApiOperation(value = "Get All Accounts", notes = "get all accounts from db", response = Account::class)
    @GetMapping("")
    fun index() = AccountRepository.findAll()

    @ApiOperation(value = "Create New Account", notes = "create new Account entity", response = Account::class)
    @PostMapping("")
    fun create(@RequestParam params: Map<String, String>): Account {
        val requestParams = RequestParams(params)
        return Account.new(requestParams)
    }

    @ApiOperation(value = "Get a Specific Account", notes = "get a specific Account by ID", response = Account::class)
    @GetMapping("{id}")
    fun read(@PathVariable id: Long) = AccountRepository.findById(id)

    @ApiOperation(value = "Update a Specific Account", notes = "update a specific Account by request params", response = Account::class)
    @PostMapping("{id}")
    fun update(@PathVariable id: Long, @RequestParam params: Map<String, String>): Account {
        val requestParams = RequestParams(params)
        return Account.new(requestParams, false, id)
    }

    @ApiOperation(value = "Delete a Specific Account", notes = "delete a specific Account passed", response = Account::class)
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val targetAccount = AccountRepository.findById(id)
        return AccountRepository.delete(targetAccount)
    }
}