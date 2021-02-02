package com.spring_boot.domain.book.value_object

import com.spring_boot.base.model.value_object.AbstractValueObjectId
import com.spring_boot.domain.book.Book
import com.spring_boot.domain.book.repository.BookRepository

class BookId(override var value: Long) : AbstractValueObjectId() {
    /**
     * @return entity which has the same ID as this ReviewId instance
     */
    override fun toEntity(): Book = BookRepository.findById(this)
}