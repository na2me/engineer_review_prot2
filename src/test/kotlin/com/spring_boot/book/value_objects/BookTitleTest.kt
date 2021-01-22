package com.spring_boot.book.value_objects

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import org.valiktor.ConstraintViolationException

class BookTitleTest {

    @Test(dataProvider = "provider")
    fun test(value: String, isValid: Boolean) {
        when (isValid) {
            true -> assertDoesNotThrow { BookTitle(value) }
            false -> assertThrows<ConstraintViolationException>  { BookTitle(value) }
        }
    }


    @DataProvider(name = "provider")
    fun dataProvider(): MutableIterator<Array<*>> {
        val testData: ArrayList<Array<*>> = arrayListOf()

        // normal scenarios
        testData.add(arrayOf("test title", true))
        testData.add(arrayOf("a", true))
        testData.add(arrayOf("a".repeat(50), true))
        // exceptional scenarios
        testData.add(arrayOf("", false))
        testData.add(arrayOf("a".repeat(51), false))

        return testData.iterator()
    }
}