package com.spring_boot.domain.book

import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.account.Account
import com.spring_boot.domain.author.Author
import com.spring_boot.domain.book.factory.BookFactory
import com.spring_boot.domain.book.repository.BookRepository
import com.spring_boot.domain.book.value_object.*
import io.swagger.annotations.ApiModelProperty
import javax.persistence.*


@Entity
@Table(name = "book")
class Book(
        @ApiModelProperty(value = "AuthorId", required = true)
        @ManyToOne
        @JoinColumn(name = "author_id")
        var author: Author,
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
        var publishedAt: BookPublishedAt) : AbstractEntity<BookId>() {

    /**
     * @return Value Object ID
     */
    override fun id() = BookId(this.id)

    /**
     * @return saved entity
     */
    override fun save() = BookRepository.save(this)

    companion object {
        /**
         * call `new` method process provided by Factory
         *
         * @return Book
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: BookId = BookId.getUnsavedId()) =
                BookFactory.new(params, isNew, id)
    }
}