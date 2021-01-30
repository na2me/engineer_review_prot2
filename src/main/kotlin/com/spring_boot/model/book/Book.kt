package com.spring_boot.model.book

import com.google.gson.Gson
import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.model.book.factory.BookFactory
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

    /**
     * @return [id] value of the entity
     */
    fun id() = this.id

    /**
     * @return json format
     */
    override fun toString(): String = Gson().toJson(this)


    companion object {
        /**
         * call `new` method process provided by BookFactory
         *
         * @return Book
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: Long = -1) =
                BookFactory.new(params, isNew, id)
    }
}