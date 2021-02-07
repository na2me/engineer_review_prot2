package com.spring_boot.domain.book.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.author.value_object.AuthorId
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
         * @return [Book]
         */
        fun new(params: RequestParams, isNew: Boolean, id: BookId): Book {
            val author = AuthorId(params.getValue("authorId").toLong()).toEntity()
            val title = BookTitle(params.getValue("title"))
            val category = BookCategory(BookCategory.Categories.valueOf(params.getValue("category")))
            val score = BookScore(params.getValue("score").toDouble())
            val url = BookUrl(params.getValue("url"))
            val description = BookDescription(params.getValue("description"))
            val publishedAt = BookPublishedAt(LocalDate.parse(params.getValue("publishedAt"), DateTimeFormatter.ISO_DATE))

            val entity: Book
            when (isNew) {
                // when the entity is newly created, prepare new entity
                true -> {
                    entity = Book(
                            author,
                            title,
                            category,
                            score,
                            url,
                            description,
                            publishedAt
                    )
                }
                // when the existed entity is updated, set each fields as new ones
                false -> {
                    entity = BookRepository.findById(id)
                    entity.author = author
                    entity.title = title
                    entity.category = category
                    entity.score = score
                    entity.url = url
                    entity.description = description
                    entity.publishedAt = publishedAt
                }
            }
            return entity.save()
        }
    }
}