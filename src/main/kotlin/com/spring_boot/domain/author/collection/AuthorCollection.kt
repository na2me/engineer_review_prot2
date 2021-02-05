package com.spring_boot.domain.author.collection

import com.spring_boot.base.collection.AbstractEntityCollection
import com.spring_boot.domain.author.Author
import com.spring_boot.domain.book.collection.BookCollection

/**
 * @return [BookCollection] converted from [List<Book>]
 */
fun List<Author>.toCollection(): AuthorCollection {
    return AuthorCollection(this)
}

class AuthorCollection(list: List<Author>) : AbstractEntityCollection<AuthorCollection, Author>(list) {
}