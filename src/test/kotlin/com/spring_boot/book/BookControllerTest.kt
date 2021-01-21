package com.spring_boot.book

import org.json.JSONArray
import org.json.JSONObject
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

    @Autowired
    lateinit var repository: BookRepository

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun testIndex() {
        val book = BookTest.entity()
        val saved = repository.save(book)

        // --------------------------------------

        // the saved book should be acquired by calling index API
        val response = mockMvc.perform(get("/api/book/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONArray(response).getJSONObject(0)
        assert(saved.title == json.get("title"))
        assert(saved.category == json.get("category"))
        assert(saved.score == json.get("score"))
        assert(saved.url == json.get("url"))
    }

    @Test
    fun testCreate() {
        val book = BookTest.entity()

        // --------------------------------------

        // the book created above should be returned as saved one by calling store API
        mockMvc.perform(post("/api/book/")
                .param("title", book.title)
                .param("category", book.category.toString())
                .param("score", book.score.toString())
                .param("url", book.url))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        // --------------------------------------

        // the book should be saved successfully
        val books = repository.findAll()
        books.contains(book)
    }

    @Test
    fun testRead() {
        val book = BookTest.entity()
        val saved = repository.save(book)

        // --------------------------------------

        // the saved book should be acquired by calling read API
        val response = mockMvc.perform(get("/api/book/${saved.id()}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        assert(saved.title == json.get("title"))
        assert(saved.category == json.get("category"))
        assert(saved.score == json.get("score"))
        assert(saved.url == json.get("url"))

    }

    @Test
    fun testUpdate() {
        val book = BookTest.entity()
        val book2 = BookTest.entity2()
        val saved = repository.save(book)

        // --------------------------------------

        // the book created above should be updated with book2 properties by calling update API
        mockMvc.perform(post("/api/book/${saved.id()}")
                .param("title", book2.title)
                .param("category", book2.category.toString())
                .param("score", book2.score.toString())
                .param("url", book2.url))
                .andExpect(status().isOk)

        // --------------------------------------

        // the book should be updated successfully
        val updated = repository.findById(saved.id()).get()

        assert(updated.title == book2.title)
        assert(updated.category == book2.category)
        assert(updated.score == book2.score)
        assert(updated.url == book2.url)
    }

    @Test
    fun testDelete() {
        val book = BookTest.entity()
        val savedBook = repository.save(book)

        // --------------------------------------

        // the book created above should be deleted with by calling delete API
        mockMvc.perform(delete("/api/book/${savedBook.id()}"))
                .andExpect(status().isOk)

        // --------------------------------------

        // the book should be deleted successfully
        assertThrows<NoSuchElementException> {
            repository.findById(savedBook.id()).get()
        }
    }
}