package com.spring_boot.base.model.value_object

abstract class AbstractValueObjectString {

    open lateinit var value: String

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other === this) return true
        if (other::class.java.simpleName != this::class.java.simpleName) return false
        other as AbstractValueObjectString
        return value == other.value
    }

    override fun hashCode() = value.hashCode()

    override fun toString() = mapOf("class" to this::class.java.simpleName,
            "value" to this.value).toString()
}