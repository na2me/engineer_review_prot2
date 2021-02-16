package com.spring_boot.domain.book.collection

import com.spring_boot.base.collection.AbstractEntityCollection
import com.spring_boot.domain.book.Book

/**
 * @return [BookCollection] converted from [List<Book>]
 */
fun List<Book>.toCollection(): BookCollection {
    return BookCollection(this)
}

class BookCollection(list: List<Book>) : AbstractEntityCollection<BookCollection, Book>(list) {

}

