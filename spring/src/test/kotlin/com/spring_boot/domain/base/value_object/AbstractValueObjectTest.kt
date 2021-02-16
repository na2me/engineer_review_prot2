package com.spring_boot.domain.base.value_object

import com.spring_boot.base.model.value_object.AbstractValueObject
import com.spring_boot.domain.book.BookTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ValueObject(override var value: String) : AbstractValueObject<String>() {
    companion object {
        /**
         * test abstract instance generation methods
         */
        fun testInstance1(): ValueObject =
                ValueObject("test instance1")

        fun testInstance2(): ValueObject =
                ValueObject("test instance2")
    }
}

class AbstractValueObjectTest {
    /**
     * test equals method
     */
    @Test
    fun testEquals() {
        val valueObject = ValueObject.testInstance1()

        assertFalse(valueObject.equals(null))

        val bookTitle = BookTest.voTitle()
        assertFalse(valueObject.equals(bookTitle))

        assertTrue(valueObject.equals(valueObject))

        assertTrue(valueObject.equals(ValueObject.testInstance1()))

        assertFalse(valueObject.equals(ValueObject.testInstance2()))
    }

    /**
     * test toString method
     */
    @Test
    fun testToString() {
        val valueObject = ValueObject.testInstance1()

        val stringedValueObject = valueObject.toString()

        assertEquals(
                "{class=ValueObject, value=test instance1}",
                stringedValueObject)
    }
}