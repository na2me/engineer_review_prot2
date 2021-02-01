package com.spring_boot.model.book.controller

import com.spring_boot.base.AbstractControllerTest
import com.spring_boot.base.util.json.getValue
import com.spring_boot.model.book.BookTest
import com.spring_boot.model.book.repository.BookRepository
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


class BookControllerTest : AbstractControllerTest<BookController>() {

    override var BASE_API = "/api/book/"

    /**
     * test Index Api
     */
    @Test
    fun testIndex() {
        val entity = BookTest.entity()
        val saved = BookRepository.save(entity)

        // --------------------------------------

        // the saved entity should be acquired by calling index API
        val response = mockMvc.perform(get(BASE_API).accept(MediaType.APPLICATION_JSON))
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
        assertEquals(json.getValue("publishedAt").toString(), (saved.publishedAt.toJsonFormat()))
    }

    /**
     * test Create Api
     */
    @Test
    fun testCreate() {
        val entity = BookTest.entity()

        // --------------------------------------

        // the entity created above should be returned as saved one by calling store API
        mockMvc.perform(post(BASE_API)
                .param("title", entity.title.value)
                .param("category", entity.category.value.toString())
                .param("score", entity.score.value.toString())
                .param("url", entity.url.value)
                .param("publishedAt", entity.publishedAt.value.toString()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        // --------------------------------------

        // the entity should be saved successfully
        val entities = BookRepository.findAll()
        entities.contains(entity)
    }

    /**
     * test Read Api
     */
    @Test
    fun testRead() {
        val entity = BookTest.entity()
        val saved = BookRepository.save(entity)

        // --------------------------------------

        // the saved entity should be acquired by calling read API
        val response = mockMvc.perform(get("$BASE_API${saved.id()}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        assertEquals(json.getValue("title"), saved.title.value)
        assertEquals(json.getValue("category"), saved.category.value.toString())
        assertEquals(json.getValue("score"), saved.score.value)
        assertEquals(json.getValue("url"), saved.url.value)
        assertEquals(json.getValue("publishedAt").toString(), (saved.publishedAt.toJsonFormat()))
    }

    /**
     * test Update Api
     */
    @Test
    fun testUpdate() {
        val saved = BookRepository.save(BookTest.entity())
        val entity = BookTest.entity2()

        // --------------------------------------

        // the saved created above should be updated with entity properties by calling update API
        mockMvc.perform(post("$BASE_API${saved.id()}")
                .param("title", entity.title.value)
                .param("category", entity.category.value.toString())
                .param("score", entity.score.value.toString())
                .param("url", entity.url.value)
                .param("publishedAt", entity.publishedAt.value.toString()))
                .andExpect(status().isOk)

        // --------------------------------------

        // the entity should be updated successfully
        val updated = BookRepository.findById(saved.id())
        assertEquals(updated.title, entity.title)
        assertEquals(updated.category, entity.category)
        assertEquals(updated.score, entity.score)
        assertEquals(updated.url, entity.url)
        assertEquals(updated.publishedAt, entity.publishedAt)
    }

    /**
     * test Delete Api
     */
    @Test
    fun testDelete() {
        val entity = BookTest.entity()
        val saved = BookRepository.save(entity)

        // --------------------------------------

        // the entity created above should be deleted with by calling delete API
        mockMvc.perform(delete("$BASE_API${saved.id()}"))
                .andExpect(status().isOk)

        // --------------------------------------

        // the entity should be deleted successfully
        assertThrows<NoSuchElementException> {
            BookRepository.findById(saved.id())
        }
    }
}
