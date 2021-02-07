package com.spring_boot.domain.author.value_object

import com.spring_boot.base.model.entity.UNSAVED_VALUE
import com.spring_boot.base.model.value_object.AbstractValueObjectId
import com.spring_boot.domain.author.Author
import com.spring_boot.domain.author.repository.AuthorRepository
import javax.persistence.Embeddable

@Embeddable
data class AuthorId(override var value: Long) : AbstractValueObjectId() {
    /**
     * @return entity which has the same ID as this ReviewId instance
     */
    override fun toEntity(): Author = AuthorRepository.findById(this)

    companion object {
        /**
         * @return self instance which has UNSAVED_VALUE as its value
         */
        fun getUnsavedId() = AuthorId(UNSAVED_VALUE)
    }
}