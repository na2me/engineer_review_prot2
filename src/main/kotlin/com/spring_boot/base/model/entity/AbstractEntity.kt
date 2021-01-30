package com.spring_boot.base.model.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

/**
 * abstract class implementing necessary information for Value Object
 */
@MappedSuperclass
abstract class AbstractEntity : BaseEntity() {

    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime? = null

    @Column(name = "updated_date", nullable = false)
    var updatedDate: LocalDateTime? = null

    /**
     * record created datetime when this entity is created
     */
    @PrePersist
    protected fun prePersist() {
        this.createdDate = LocalDateTime.now()
        this.updatedDate = this.createdDate
    }

    /**
     * record updated datetime when this entity is updated
     */
    @PreUpdate
    protected fun preUpdate() {
        this.updatedDate = LocalDateTime.now()
    }
}