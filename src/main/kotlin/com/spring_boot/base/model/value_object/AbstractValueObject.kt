package com.spring_boot.base.model.value_object

abstract class AbstractValueObject<T : Any> {

    open lateinit var value: T

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other::class.java.simpleName != this::class.java.simpleName) return false
        if (other === this) return true
        other as AbstractValueObject<*>
        return value == other.value
    }

    override fun hashCode() = value.hashCode()

    override fun toString() =
            mapOf("class" to this::class.java.simpleName,
                    "value" to this.value).toString()
}