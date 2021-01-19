package com.spring_boot.book

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/")
class BookController(private val repository: BookRepository) {

    @GetMapping("")
    fun index() = repository.findAll()


    @PostMapping("")
    fun store(@ModelAttribute book: Book) = repository.save(book)


}