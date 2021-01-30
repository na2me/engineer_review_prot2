package com.spring_boot.base.model.entity

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

const val UNSAVED_ID: Long = -1L

/**
 * abstract class implementing base methods for Entity
 */
@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected val id: Long = UNSAVED_ID

    /**
     * @return true if the entity is saved, false if not
     */
    fun isSaved(): Boolean {
        return id != UNSAVED_ID
    }

    /**
     * @return true if [other] is referring the same Entity, false if not
     */
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other.javaClass != this.javaClass) return false
        if (other is BaseEntity) return sameIdentityAs(other)

        return false
    }

    /**
     * @return true if [other] has the same id as this entity
     */
    protected fun sameIdentityAs(other: BaseEntity): Boolean {
        if (id == UNSAVED_ID || other.id == UNSAVED_ID) {
            return false
        }
        return this.id == other.id
    }

    /**
     * @return hashed code of [id]
     */
    override fun hashCode(): Int {
        return if (id != UNSAVED_ID) id.hashCode() else 0
    }

}