package com.spring_boot.domain.review

import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.account.Account
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.factory.BookFactory
import com.spring_boot.domain.review.value_object.ReviewScore
import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

@Entity
@Table(name = "review")
class Review(
        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: Account,
        @ManyToOne
        @JoinColumn(name = "book_id")
        var book: Book,
        @ApiModelProperty(value = "Score", required = true)
        @Embedded
        var score: ReviewScore) : AbstractEntity() {

    companion object {
        /**
         * call `new` method process provided by Factor
         *
         * @return Book
         */
//        fun new(params: RequestParams, isNew: Boolean = true, id: Long = -1) =
//                ReviewFactory.new(params, isNew, id)
    }
}