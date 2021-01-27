package com.spring_boot.model.book

import com.google.gson.Gson
import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.model.book.repository.BookRepository
import com.spring_boot.model.book.value_objects.*
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

    fun update(newBook: Book): Book {
        title = newBook.title
        category = newBook.category
        score = newBook.score
        url = newBook.url
        publishedAt = newBook.publishedAt
        return BookRepository.save(this)
    }
}