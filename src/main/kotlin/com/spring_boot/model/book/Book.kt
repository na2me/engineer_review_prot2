package com.spring_boot.model.book

import com.google.gson.Gson
import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.model.book.repository.BookRepository
import com.spring_boot.model.book.value_objects.*
import org.apache.tomcat.jni.Local
import java.security.InvalidKeyException
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

    fun update(params: Map<String, String>): Book {
        title = BookTitle(params.getValue("title"))
        category = BookCategory(Categories.valueOf(params.getValue("category")))
        score = BookScore(params.getValue("score").toDouble())
        url = BookUrl(params.getValue("url"))
        publishedAt = BookPublishedAt(LocalDate.parse(params.getValue("publishedAt"), DateTimeFormatter.ISO_DATE))
        return BookRepository.save(this)
    }
}

fun Map<String, String>.getValue(key: String) =
        this[key] ?: throw InvalidKeyException()