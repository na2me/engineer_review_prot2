package com.spring_boot.domain.book.controller

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.repository.BookRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/")
@Api(description = "Book Controller")
class BookController {

    @ApiOperation(value = "Get All Books", notes = "get all books from db", response = Book::class)
    @GetMapping("")
    fun index() = BookRepository.findAll()

    @ApiOperation(value = "Create New Book", notes = "create new Book entity", response = Book::class)
    @PostMapping("")
    fun create(@RequestParam params: Map<String, String>): Book {
        val requestParams = RequestParams(params)
        return Book.new(requestParams)
    }

    @ApiOperation(value = "Get a Specific Book", notes = "get a specific Book by ID", response = Book::class)
    @GetMapping("{id}")
    fun read(@PathVariable id: Long) = BookRepository.findById(id)

    @ApiOperation(value = "Update a Specific Book", notes = "update a specific Book by request params", response = Book::class)
    @PostMapping("{id}")
    fun update(@PathVariable id: Long, @RequestParam params: Map<String, String>): Book {
        val requestParams = RequestParams(params)
        return Book.new(requestParams, false, id)
    }

    @ApiOperation(value = "Delete a Specific Book", notes = "delete a specific Book passed", response = Book::class)
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val targetBook = BookRepository.findById(id)
        return BookRepository.delete(targetBook)
    }
}