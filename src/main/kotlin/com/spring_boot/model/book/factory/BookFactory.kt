package com.spring_boot.model.book.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.model.book.Book
import com.spring_boot.model.book.repository.BookRepository
import com.spring_boot.model.book.value_objects.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BookFactory {
    companion object {
        /**
         * create or update instance by referring [params]
         * if [isNew] is false, need [id] to specify the target entity
         *
         * @return Book
         */
        fun new(params: RequestParams, isNew: Boolean, id: Long): Book {
            val title = BookTitle(params.getValue("title"))
            val category = BookCategory(BookCategory.Categories.valueOf(params.getValue("category")))
            val score = BookScore(params.getValue("score").toDouble())
            val url = BookUrl(params.getValue("url"))
            val publishedAt = BookPublishedAt(LocalDate.parse(params.getValue("publishedAt"), DateTimeFormatter.ISO_DATE))

            val book: Book
            when (isNew) {
                true -> {
                    book = Book(
                            title,
                            category,
                            score,
                            url,
                            publishedAt
                    )
                }
                false -> {
                    book = BookRepository.findById(id)
                    book.title = title
                    book.category = category
                    book.score = score
                    book.url = url
                    book.publishedAt = publishedAt
                }
            }
            return BookRepository.save(book)
        }
    }
}