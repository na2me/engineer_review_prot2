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

    /**
     * setup mockMvc DI for each test methods
     */
    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    /**
     * test Index Api
     */
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
        assertEquals(json.getValue("score"), saved.score.value)
        assertEquals(json.getValue("url"), saved.url.value)
        // todo: deal with Json format
//        assertEquals(json.getValue("publishedAt"), saved.publishedAt.toJsonFormat())
    }

    /**
     * test Create Api
     */
    @Test
    fun testCreate() {
        val book = BookTest.entity()

        // --------------------------------------

        // the book created above should be returned as saved one by calling store API
        mockMvc.perform(post("/api/book/")
                .param("title", book.title.value)
                .param("category", book.category.value.toString())
                .param("score", book.score.value.toString())
                .param("url", book.url.value)
                .param("publishedAt", book.publishedAt.value.toString()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        // --------------------------------------

        // the book should be saved successfully
        val books = BookRepository.findAll()
        books.contains(book)
    }

    /**
     * test Read Api
     */
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
        println(json.getJSONObject("title").getString("value"))
        assertEquals(json.getValue("title"), saved.title.value)
        assertEquals(json.getValue("category"), saved.category.value.toString())
        assertEquals(json.getValue("score"), saved.score.value)
        assertEquals(json.getValue("url"), saved.url.value)
        // todo: deal with Json format
//        assertEquals(json.getValue("publishedAt"), saved.publishedAt.toJsonFormat())
    }

    /**
     * test Update Api
     */
    @Test
    fun testUpdate() {
        val saved = BookRepository.save(BookTest.entity())
        val book = BookTest.entity2()

        // --------------------------------------

        // the book created above should be updated with book2 properties by calling update API
        mockMvc.perform(post("/api/book/${saved.id()}")
                .param("title", book.title.value)
                .param("category", book.category.value.toString())
                .param("score", book.score.value.toString())
                .param("url", book.url.value)
                .param("publishedAt", book.publishedAt.value.toString()))
                .andExpect(status().isOk)

        // --------------------------------------

        // the book should be updated successfully
        val updated = BookRepository.findById(saved.id())
        assertEquals(updated.title, book.title)
        assertEquals(updated.category, book.category)
        assertEquals(updated.score, book.score)
        assertEquals(updated.url, book.url)
        assertEquals(updated.publishedAt, book.publishedAt)
    }

    /**
     * test Delete Api
     */
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
            BookRepository.findById(savedBook.id())
        }
    }
}

fun JSONObject.getValue(field: String) =
        JSONObject(this.getString(field)).getString("value")
