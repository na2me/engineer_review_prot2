package com.spring_boot.model.book

import com.google.gson.Gson
import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.model.book.repository.BookRepository
import com.spring_boot.model.book.value_objects.*
import org.apache.tomcat.jni.Local
import java.security.InvalidKeyException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
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

    companion object {
        fun update(id: Long, params: RequestParams): Book {
            val targetBook = BookRepository.findById(id)

            targetBook.title = BookTitle(params.getValue("title"))
            targetBook.category = BookCategory(Categories.valueOf(params.getValue("category")))
            targetBook.score = BookScore(params.getValue("score").toDouble())
            targetBook.url = BookUrl(params.getValue("url"))
            targetBook.publishedAt = BookPublishedAt(LocalDate.parse(params.getValue("publishedAt"), DateTimeFormatter.ISO_DATE))
            return BookRepository.save(targetBook)
        }
    }

}