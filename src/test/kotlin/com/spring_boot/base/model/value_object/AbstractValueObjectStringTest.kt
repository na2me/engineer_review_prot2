package com.spring_boot.base.model.value_object

import com.spring_boot.model.book.value_objects.BookTitle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ValueObjectString(override var value: String) : AbstractValueObjectString() {
    companion object {
        fun testInstance1(): ValueObjectString =
                ValueObjectString("test instance1")

        fun testInstance2(): ValueObjectString =
                ValueObjectString("test instance2")
    }
}

class AbstractValueObjectStringTest {
    @Test
    fun testEquals() {
        val valueObject = ValueObjectString.testInstance1()

        assertFalse(valueObject.equals(null))

        val bookTitle = BookTitle("test title")
        assertFalse(valueObject.equals(bookTitle))

        assertTrue(valueObject.equals(valueObject))

        assertTrue(valueObject.equals(ValueObjectString.testInstance1()))

        assertFalse(valueObject.equals(ValueObjectString.testInstance2()))
    }

    @Test
    fun testToString() {
        val valueObject = ValueObjectString.testInstance1()

        val stringedValueObject = valueObject.toString()

        assertEquals(
                "{class=ValueObjectString, value=test instance1}",
                stringedValueObject)
    }
}