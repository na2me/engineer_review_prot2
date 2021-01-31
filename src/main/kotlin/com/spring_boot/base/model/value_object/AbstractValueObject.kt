package com.spring_boot.base.model.value_object

import javax.persistence.MappedSuperclass

/**
 * abstract class implementing common methods of Value Object
 */
@MappedSuperclass
abstract class AbstractValueObject<T : Any> {

    lateinit var value: T

    /**
     * @return true if [other] is referring the same Value Object, false if not
     */
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other::class.java.simpleName != this::class.java.simpleName) return false
        if (other === this) return true
        other as AbstractValueObject<*>
        return value == other.value
    }

    /**
     * @return hashed code of [value]
     */
    override fun hashCode() = value.hashCode()

    /**
     * print format
     */
    override fun toString() =
            mapOf("class" to this::class.java.simpleName,
                    "value" to value).toString()
}