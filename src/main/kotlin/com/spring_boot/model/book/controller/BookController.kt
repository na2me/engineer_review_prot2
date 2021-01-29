package com.spring_boot.model.book.controller

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.model.book.Book
import com.spring_boot.model.book.repository.BookRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/")
class BookController {

    @GetMapping("")
    fun index() = BookRepository.findAll()

    @PostMapping("")
    fun create(@RequestParam params: Map<String, String>): Book {
        val requestParams = RequestParams(params)
        return Book.new(requestParams)
    }

    @GetMapping("{id}")
    fun read(@PathVariable id: Long) = BookRepository.findById(id)

    @PostMapping("{id}")
    fun update(@PathVariable id: Long, @RequestParam params: Map<String, String>): Book {
        val requestParams = RequestParams(params)
        return Book.new(requestParams, false, id)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val targetBook = BookRepository.findById(id)
        return BookRepository.delete(targetBook)
    }
}