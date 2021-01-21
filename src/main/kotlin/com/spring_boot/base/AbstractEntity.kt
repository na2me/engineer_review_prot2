package com.spring_boot.base

import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class AbstractEntity : BaseEntity() {

    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime? = null

    @Column(name = "updated_date", nullable = false)
    var updatedDate: LocalDateTime? = null

    @PrePersist
    protected fun prePersist() {
        this.createdDate = LocalDateTime.now()
        this.updatedDate = this.createdDate
    }

    @PreUpdate
    protected fun preUpdate() {
        this.updatedDate = LocalDateTime.now()
    }

    abstract fun isSatisfied()
}