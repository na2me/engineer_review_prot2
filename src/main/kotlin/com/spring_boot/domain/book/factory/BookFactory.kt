package com.spring_boot.domain.book.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.repository.BookRepository
import com.spring_boot.domain.book.value_object.*
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
        fun new(params: RequestParams, isNew: Boolean, id: BookId): Book {
            val title = BookTitle(params.getValue("title"))
            val category = BookCategory(BookCategory.Categories.valueOf(params.getValue("category")))
            val score = BookScore(params.getValue("score").toDouble())
            val url = BookUrl(params.getValue("url"))
            val publishedAt = BookPublishedAt(LocalDate.parse(params.getValue("publishedAt"), DateTimeFormatter.ISO_DATE))

            val book: Book
            when (isNew) {
                // when the entity is newly created, prepare new entity
                true -> {
                    book = Book(
                            title,
                            category,
                            score,
                            url,
                            publishedAt
                    )
                }
                // when the existed entity is updated, set each fields as new ones
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