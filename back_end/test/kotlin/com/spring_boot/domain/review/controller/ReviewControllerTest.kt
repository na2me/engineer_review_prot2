package com.spring_boot.domain.review.controller

import com.google.gson.Gson
import com.spring_boot.base.AbstractControllerTest
import com.spring_boot.base.util.json.getCollectionElements
import com.spring_boot.base.util.json.getForeignKeyOf
import com.spring_boot.base.util.json.getId
import com.spring_boot.domain.review.ReviewTest
import com.spring_boot.domain.review.repository.ReviewRepository
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

class ReviewControllerTest : AbstractControllerTest<ReviewController>() {

    override var BASE_API = "/api/review"

    /**
     * test Index Api
     */
    @Test
    fun testIndex() {
        ReviewTest.entity().save()

        // --------------------------------------

        // the saved entity should be acquired by calling index API
        val response = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // acquired entity should be 1 as it's saved first
        val json = JSONObject(response)
        assertEquals(1, json.getCollectionElements().count())
    }

    /**
     * test Create Api
     */
    @Test
    fun testCreate() {
        val entity = ReviewTest.entity()

        // --------------------------------------

        val json = Gson().toJson(
                mapOf(
                        "accountId" to entity.account.id().value.toString(),
                        "bookId" to entity.book.id().value.toString(),
                        "score" to entity.score.value.toString(),
                )
        )

        // --------------------------------------

        // the entity created above should be returned as saved one by calling store API
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))

        // --------------------------------------

        // the entity should be saved successfully
        val entities = ReviewRepository.findAll()
        entities.contains(entity)
    }

    /**
     * test Read Api
     */
    @Test
    fun testRead() {
        val saved = ReviewTest.entity().save()

        // --------------------------------------

        // the saved entity should be acquired by calling read API
        val response = mockMvc.perform(MockMvcRequestBuilders.get("$BASE_API/${saved.id().value}").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        assertEquals(json.getForeignKeyOf("account"), saved.account.id().value)
        assertEquals(json.getForeignKeyOf("book"), saved.book.id().value)
        assertEquals(json.getId(), saved.id().value)
    }

    /**
     * test Update Api
     */
    @Test
    fun testUpdate() {
        val saved = ReviewTest.entity().save()
        val entity2 = ReviewTest.entity2()

        // --------------------------------------

        val json = Gson().toJson(
                mapOf(
                        "accountId" to entity2.account.id().value.toString(),
                        "bookId" to entity2.book.id().value.toString(),
                        "score" to entity2.score.value.toString(),
                )
        )

        // --------------------------------------

        // the "saved" created above should be updated with "entity" properties by calling update API
        mockMvc.perform(MockMvcRequestBuilders.put("$BASE_API/${saved.id().value}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be updated successfully
        val updated = ReviewRepository.findById(saved.id())
        assertEquals(updated.account.id(), entity2.account.id())
        assertEquals(updated.book.id(), entity2.book.id())
        assertEquals(updated.score, entity2.score)
    }

    /**
     * test Delete Api
     */
    @Test
    fun testDelete() {
        val saved = ReviewTest.entity().save()

        // --------------------------------------

        // the entity created above should be deleted with by calling delete API
        mockMvc.perform(MockMvcRequestBuilders.delete("$BASE_API/${saved.id().value}"))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be deleted successfully
        assertThrows<NoSuchElementException> {
            ReviewRepository.findById(saved.id())
        }
    }
}