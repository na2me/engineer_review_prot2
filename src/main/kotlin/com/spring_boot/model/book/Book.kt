package com.spring_boot.model.book

import com.google.gson.Gson
import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.model.book.repository.BookRepository
import com.spring_boot.model.book.value_objects.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "book")
class Book(
        @Embedded var title: BookTitle,
        @Embedded var category: BookCategory,
        @Embedded var score: BookScore,
        @Embedded var url: BookUrl,
        @Embedded var publishedAt: BookPublishedAt) : AbstractEntity() {

    fun id() = this.id

    override fun toString(): String = Gson().toJson(this)

    // todo: move this to factory trait
    companion object {
        fun new(params: RequestParams, isNew: Boolean = true, id: Long = -1): Book {
            val title = BookTitle(params.getValue("title"))
            val category = BookCategory(Categories.valueOf(params.getValue("category")))
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