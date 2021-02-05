package com.spring_boot.domain.author

import com.spring_boot.base.model.entity.AbstractEntity
import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.author.factory.AuthorFactory
import com.spring_boot.domain.author.repository.AuthorRepository
import com.spring_boot.domain.author.value_object.AuthorBiography
import com.spring_boot.domain.author.value_object.AuthorId
import com.spring_boot.domain.author.value_object.AuthorName
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "author")
class Author(
        @ApiModelProperty(value = "Name", required = true)
        @Embedded
        var name: AuthorName,
        @ApiModelProperty(value = "Biography", required = true)
        @Embedded
        var biography: AuthorBiography) : AbstractEntity<AuthorId>() {

    /**
     * @return Value Object ID
     */
    override fun id() = AuthorId(this.id)

    /**
     * @return saved entity
     */
    override fun save() = AuthorRepository.save(this)

    companion object {
        /**
         * call `new` method process provided by Factory
         *
         * @return Book
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: AuthorId = AuthorId.getUnsavedId()) =
                AuthorFactory.new(params, isNew, id)
    }
}