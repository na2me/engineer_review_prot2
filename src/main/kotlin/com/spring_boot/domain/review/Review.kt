package com.spring_boot.domain.review

import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.account.Account
import com.spring_boot.domain.account.value_object.AccountId
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.repository.BookRepository
import com.spring_boot.domain.book.value_object.BookId
import com.spring_boot.domain.book.value_object.BookScore
import com.spring_boot.domain.review.factory.ReviewFactory
import com.spring_boot.domain.review.repository.ReviewRepository
import com.spring_boot.domain.review.value_object.ReviewId
import com.spring_boot.domain.review.value_object.ReviewScore
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "review")
class Review(
        @ApiModelProperty(value = "AccountId", required = true)
        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: Account,
        @ApiModelProperty(value = "BookId", required = true)
        @ManyToOne
        @JoinColumn(name = "book_id")
        var book: Book,
        @ApiModelProperty(value = "Score", required = true)
        @Embedded
        var score: ReviewScore) : AbstractEntity<ReviewId>() {

    /**
     * @return Value Object ID
     */
    override fun id() = ReviewId(this.id)

    /**
     * @return saved entity
     */
    override fun save(): Review {
        val saved = ReviewRepository.save(this)
        // need to recalculate the associated book's score
        // to reflect newly added review for that book
        reCalculateBookScore(saved)
        return saved
    }

    companion object {
        /**
         * call `new` method process provided by Factor
         *
         * @return Book
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: ReviewId = ReviewId.getUnsavedId()) =
                ReviewFactory.new(params, isNew, id)
    }

    // --------------------------------------

    /**
     * recalculate BookScore associated with this Review
     */
    fun reCalculateBookScore(review: Review) {
        val associatedBook = review.book
        val reviews = ReviewRepository.findAllByBookId(associatedBook.id())

        //TODO: make list as Entity Collection
        val averageScore = reviews.map { it.score.value }.average()

        associatedBook.score = BookScore(averageScore)
        associatedBook.save()
    }
}