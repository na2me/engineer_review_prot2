package com.spring_boot.domain.author.factory

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.author.Author
import com.spring_boot.domain.author.repository.AuthorRepository
import com.spring_boot.domain.author.value_object.AuthorBiography
import com.spring_boot.domain.author.value_object.AuthorId
import com.spring_boot.domain.author.value_object.AuthorName
import com.spring_boot.domain.book.value_object.BookId

class AuthorFactory {
    companion object {
        /**
         * create or update instance by referring [params]
         * if [isNew] is false, need [id] to specify the target entity
         *
         * @return [Author]
         */
        fun new(params: RequestParams, isNew: Boolean, id: AuthorId): Author {
            val book = BookId(params.getValue("bookId").toLong()).toEntity()
            val name = AuthorName(params.getValue("name"))
            val biography = AuthorBiography(params.getValue("biography"))

            val entity: Author
            when (isNew) {
                // when the entity is newly created, prepare new entity
                true -> {
                    entity = Author(
                            book,
                            name,
                            biography,
                    )
                }
                // when the existed entity is updated, set each fields as new ones
                false -> {
                    entity = AuthorRepository.findById(id)
                    entity.book = book
                    entity.name = name
                    entity.biography = biography
                }
            }
            return entity.save()
        }
    }
}