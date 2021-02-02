package com.spring_boot.base.model.value_object

import com.spring_boot.base.model.entity.AbstractEntity
import javax.persistence.MappedSuperclass

const val UNSAVED_VALUE = -1L

/**
 * abstract class defining abstract methods of Value Object ID
 */
@MappedSuperclass
abstract class AbstractValueObjectId : AbstractValueObject<Long>() {
    override var value: Long = UNSAVED_VALUE

    /**
     * @return entity which has the same ID as this AbstractValueObjectId instance
     */
    abstract fun toEntity() : AbstractEntity<*>
}