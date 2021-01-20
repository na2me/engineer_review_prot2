package com.spring_boot.book

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/")
class BookController(private val repository: BookRepository) {

    @GetMapping("")
    fun index() = repository.findAll()

    @PostMapping("")
    fun create(@ModelAttribute book: Book) = repository.save(book)

    @GetMapping("{id}")
    fun read(@PathVariable id: Long) = repository.findById(id)

    @PostMapping("{id}")
    fun update(@PathVariable id: Long, @ModelAttribute book: Book) = repository.save(book)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val book = repository.findById(id)
        return book.ifPresent { repository.delete(it) }
    }
}