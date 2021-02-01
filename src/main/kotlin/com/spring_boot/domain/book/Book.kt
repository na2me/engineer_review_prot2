package com.spring_boot.domain.book

import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.book.factory.BookFactory
import com.spring_boot.domain.book.value_object.*
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "book")
class Book(
        @ApiModelProperty(value = "Title", required = true)
        @Embedded
        var title: BookTitle,
        @ApiModelProperty(value = "Category", required = true)
        @Embedded
        var category: BookCategory,
        @ApiModelProperty(value = "Score", required = true)
        @Embedded
        var score: BookScore,
        @ApiModelProperty(value = "URL", required = true)
        @Embedded
        var url: BookUrl,
        @ApiModelProperty(value = "PublishedAt", required = true)
        @Embedded
        var publishedAt: BookPublishedAt) : AbstractEntity() {

    companion object {
        /**
         * call `new` method process provided by Factory
         *
         * @return Book
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: Long = -1) =
                BookFactory.new(params, isNew, id)
    }
}