package com.spring_boot.domain.author.controller

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.author.Author
import com.spring_boot.domain.author.repository.AuthorRepository
import com.spring_boot.domain.author.value_object.AuthorId
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/author/")
@Api(description = "Author Controller")
class AuthorController {

    @ApiOperation(value = "Get All Authors", notes = "get all Authors from db", response = Author::class)
    @GetMapping("")
    fun index() = AuthorRepository.findAll()

    @ApiOperation(value = "Create New Author", notes = "create new Author entity", response = Author::class)
    @PostMapping("")
    fun create(@RequestParam params: Map<String, String>): Author {
        val requestParams = RequestParams(params)
        return Author.new(requestParams)
    }

    @ApiOperation(value = "Get a Specific Author", notes = "get a specific Author by ID", response = Author::class)
    @GetMapping("{id}")
    fun read(@PathVariable id: Long) = AuthorRepository.findById(AuthorId(id))

    @ApiOperation(value = "Update a Specific Author", notes = "update a specific Author by request params", response = Author::class)
    @PostMapping("{id}")
    fun update(@PathVariable id: Long, @RequestParam params: Map<String, String>): Author {
        val requestParams = RequestParams(params)
        return Author.new(requestParams, false, AuthorId(id))
    }

    @ApiOperation(value = "Delete a Specific Author", notes = "delete a specific Author passed", response = Author::class)
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val targetEntity = AuthorRepository.findById(AuthorId(id))
        return AuthorRepository.delete(targetEntity)
    }
}