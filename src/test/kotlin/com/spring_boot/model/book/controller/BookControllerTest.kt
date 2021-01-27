package com.spring_boot.model.book.controller

import com.spring_boot.model.book.BookTest
import com.spring_boot.model.book.repository.BookRepository
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*


@SpringBootTest
class BookControllerTest {

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var controller: BookController

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun testIndex() {
        val book = BookTest.entity()
        val saved = BookRepository.save(book)

        // --------------------------------------

        // the saved book should be acquired by calling index API
        val response = mockMvc.perform(get("/api/book/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONArray(response).getJSONObject(0)
        assertEquals(json.getValue("title"), saved.title.value)
        assertEquals(json.getValue("category"), saved.category.value.toString())
        assertEquals(json.getValue("score"), saved.score.value.toString())
        assertEquals(json.getValue("url"), saved.url.value)
        assertEquals(json.getValue("publishedAt"), saved.publishedAt.toJsonFormat())
    }

    @Test
    fun testCreate() {
        val book = BookTest.entity()

        // --------------------------------------

        // the book created above should be returned as saved one by calling store API
        mockMvc.perform(post("/api/book/").flashAttr("book", book))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        // --------------------------------------

        // the book should be saved successfully
        val books = BookRepository.findAll()
        books.contains(book)
    }

    @Test
    fun testRead() {
        val book = BookTest.entity()
        val saved = BookRepository.save(book)

        // --------------------------------------

        // the saved book should be acquired by calling read API
        val response = mockMvc.perform(get("/api/book/${saved.id()}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        assertEquals(json.getValue("title"), saved.title.value)
        assertEquals(json.getValue("category"), saved.category.value.toString())
        assertEquals(json.getValue("score"), saved.score.value.toString())
        assertEquals(json.getValue("url"), saved.url.value)
        assertEquals(json.getValue("publishedAt"), saved.publishedAt.toJsonFormat())
    }

    @Test
    fun testUpdate() {
        val book = BookTest.entity()
        val book2 = BookTest.entity2()
        val saved = BookRepository.save(book)

        // --------------------------------------

        // the book created above should be updated with book2 properties by calling update API
        mockMvc.perform(post("/api/book/${saved.id()}").flashAttr("book", book2))
                .andExpect(status().isOk)

        // --------------------------------------

        // the book should be updated successfully
        val updated = BookRepository.findById(saved.id()).get()
        assertEquals(book2.title, updated.title)
        assertEquals(updated.category, book2.category)
        assertEquals(updated.score, book2.score)
        assertEquals(updated.url, book2.url)
        assertEquals(updated.publishedAt, book2.publishedAt)

    }

    @Test
    fun testDelete() {
        val book = BookTest.entity()
        val savedBook = BookRepository.save(book)

        // --------------------------------------

        // the book created above should be deleted with by calling delete API
        mockMvc.perform(delete("/api/book/${savedBook.id()}"))
                .andExpect(status().isOk)

        // --------------------------------------

        // the book should be deleted successfully
        assertThrows<NoSuchElementException> {
            BookRepository.findById(savedBook.id()).get()
        }
    }
}

fun JSONObject.getValue(field: String) =
        JSONObject(this.getString(field)).getString("value")
