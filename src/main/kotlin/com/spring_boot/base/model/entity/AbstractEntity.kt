package com.spring_boot.base.model.entity

import com.spring_boot.base.model.value_object.AbstractValueObjectId
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime
import javax.persistence.*

const val UNSAVED_VALUE = -1L

/**
 * abstract class implementing necessary information for Value Object
 */
@MappedSuperclass
abstract class AbstractEntity<T : AbstractValueObjectId> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "entity ID", required = false)
    // TODO: make the field protected and output as json element at the same time
    var id: Long = UNSAVED_VALUE

    /**
     * @return Value Object ID
     */
    abstract fun id(): T

    /**
     * @return saved entity
     */
    abstract fun save(): AbstractEntity<T>

    /**
     * @return true if the entity is saved, false if not
     */
    fun isSaved(): Boolean {
        return id != UNSAVED_VALUE
    }

    // --------------------------------------

    @ApiModelProperty(value = "when it was created", required = false)
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime? = null

    @ApiModelProperty(value = "when it was updated", required = false)
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

    // --------------------------------------

    /**
     * @return true if [other] is referring the same Entity, false if not
     */
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other.javaClass != this.javaClass) return false
        if (other is AbstractEntity<*>) return sameIdentityAs(other)

        return false
    }

    /**
     * @return true if [other] has the same id as this entity
     */
    protected fun sameIdentityAs(other: AbstractEntity<*>): Boolean {
        if (id == UNSAVED_VALUE || other.id == UNSAVED_VALUE) {
            return false
        }
        return this.id == other.id
    }

    /**
     * @return hashed code of [id]
     */
    override fun hashCode(): Int {
        return if (id != UNSAVED_VALUE) id.hashCode() else 0
    }
}