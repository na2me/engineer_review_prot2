package com.spring_boot.domain.book.controller

import com.google.gson.Gson
import com.spring_boot.base.AbstractControllerTest
import com.spring_boot.base.util.json.getCollectionElements
import com.spring_boot.base.util.json.getValue
import com.spring_boot.domain.book.BookTest
import com.spring_boot.domain.book.repository.BookRepository
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


class BookControllerTest : AbstractControllerTest<BookController>() {

    override var BASE_API = "/api/book"

    /**
     * test Index Api
     */
    @Test
    fun testIndex() {
        BookTest.entity().save()

        // --------------------------------------

        // the saved entity should be acquired by calling index API
        val response = mockMvc.perform(get(BASE_API).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // acquired entity should be 1 as it's saved first
        val json = JSONObject(response)
        Assertions.assertEquals(1, json.getCollectionElements().count())
    }

    /**
     * test Create Api
     */
    @Test
    fun testCreate() {
        val entity = BookTest.entity()

        // --------------------------------------

        val json = Gson().toJson(
                mapOf(
                        "authorId" to entity.author.id().value.toString(),
                        "title" to entity.title.value,
                        "category" to entity.category.value.toString(),
                        "rating" to entity.rating.value.toString(),
                        "url" to entity.url.value,
                        "description" to entity.description.value,
                        "publishedAt" to entity.publishedAt.value.toString()
                )
        )

        // --------------------------------------

        // the entity created above should be returned as saved one by calling store API
        mockMvc.perform(post(BASE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
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
        val saved = BookTest.entity().save()

        // --------------------------------------

        // the saved entity should be acquired by calling read API
        val response = mockMvc.perform(get("$BASE_API/${saved.id().value}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        assertEquals(json.getValue("title"), saved.title.value)
        assertEquals(json.getValue("category"), saved.category.value.toString())
        assertEquals(json.getValue("rating"), saved.rating.value)
        assertEquals(json.getValue("url"), saved.url.value)
        assertEquals(json.getValue("publishedAt").toString(), (saved.publishedAt.toJsonFormat()))
    }

    /**
     * test Update Api
     */
    @Test
    fun testUpdate() {
        val saved = BookTest.entity().save()
        val entity2 = BookTest.entity2()

        // --------------------------------------

        val json = Gson().toJson(
                mapOf(
                        "authorId" to entity2.author.id().value.toString(),
                        "title" to entity2.title.value,
                        "category" to entity2.category.value.toString(),
                        "rating" to entity2.rating.value.toString(),
                        "url" to entity2.url.value,
                        "description" to entity2.description.value,
                        "publishedAt" to entity2.publishedAt.value.toString()
                )
        )

        // --------------------------------------

        // the saved created above should be updated with entity properties by calling update API
        mockMvc.perform(put("$BASE_API/${saved.id().value}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk)

        // --------------------------------------

        // the entity should be updated successfully
        val updated = BookRepository.findById(saved.id())
        assertEquals(updated.author.id(), entity2.author.id())
        assertEquals(updated.title, entity2.title)
        assertEquals(updated.category, entity2.category)
        assertEquals(updated.rating, entity2.rating)
        assertEquals(updated.url, entity2.url)
        assertEquals(updated.publishedAt, entity2.publishedAt)
    }

    /**
     * test Delete Api
     */
    @Test
    fun testDelete() {
        val saved = BookTest.entity().save()

        // --------------------------------------

        // the entity created above should be deleted with by calling delete API
        mockMvc.perform(delete("$BASE_API/${saved.id().value}"))
                .andExpect(status().isOk)

        // --------------------------------------

        // the entity should be deleted successfully
        assertThrows<NoSuchElementException> {
            BookRepository.findById(saved.id())
        }
    }
}
