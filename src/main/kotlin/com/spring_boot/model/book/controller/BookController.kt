package com.spring_boot.model.book.controller

import com.spring_boot.model.book.Book
import com.spring_boot.model.book.repository.BookRepository
import org.springframework.web.bind.annotation.*
import java.util.*

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
    fun update(@PathVariable id: Long, @ModelAttribute book: Book) {
        val targetBook: Optional<Book> = repository.findById(id)
        targetBook.ifPresent {
            it.title = book.title
            it.category = book.category
            it.score = book.score
            it.url = book.url
            repository.save(it)
        }
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val book = repository.findById(id)
        return book.ifPresent { repository.delete(it) }
    }
}