package com.spring_boot.domain.book.value_object

import com.spring_boot.base.model.value_object.AbstractValueObjectId
import com.spring_boot.base.model.value_object.UNSAVED_VALUE
import com.spring_boot.domain.account.value_object.AccountId
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.repository.BookRepository
import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class BookId(override var value: Long) : AbstractValueObjectId(), Serializable {
    /**
     * @return entity which has the same ID as this ReviewId instance
     */
    override fun toEntity(): Book = BookRepository.findById(this)

    companion object {
        /**
         * @return self instance which has UNSAVED_VALUE as its value
         */
        fun getUnsaved() = BookId(UNSAVED_VALUE)
    }
}