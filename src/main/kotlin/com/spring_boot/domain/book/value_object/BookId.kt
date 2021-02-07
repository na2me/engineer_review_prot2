package com.spring_boot.domain.book.value_object

import com.spring_boot.base.model.entity.UNSAVED_VALUE
import com.spring_boot.base.model.value_object.AbstractValueObjectId
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.repository.BookRepository
import javax.persistence.Embeddable

@Embeddable
data class BookId(override var value: Long) : AbstractValueObjectId() {
    /**
     * @return entity which has the same ID as this ReviewId instance
     */
    override fun toEntity(): Book = BookRepository.findById(this)

    companion object {
        /**
         * @return self instance which has UNSAVED_VALUE as its value
         */
        fun getUnsavedId() = BookId(UNSAVED_VALUE)
    }
}