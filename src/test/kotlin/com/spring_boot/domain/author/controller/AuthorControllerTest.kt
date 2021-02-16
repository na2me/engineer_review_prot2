package com.spring_boot.domain.author.controller

import com.google.gson.Gson
import com.spring_boot.base.AbstractControllerTest
import com.spring_boot.base.util.json.getCollectionElements
import com.spring_boot.base.util.json.getValue
import com.spring_boot.domain.author.AuthorTest
import com.spring_boot.domain.author.repository.AuthorRepository
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

class AuthorControllerTest : AbstractControllerTest<AuthorController>() {

    override var BASE_API = "/api/author"

    /**
     * test Index Api
     */
    @Test
    fun testIndex() {
        AuthorTest.entity().save()

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
        val entity = AuthorTest.entity()

        // --------------------------------------

        val json = Gson().toJson(
                mapOf(
                        "name" to entity.name.value,
                        "biography" to entity.biography.value)
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
        val entities = AuthorRepository.findAll()
        entities.contains(entity)
    }

    /**
     * test Read Api
     */
    @Test
    fun testRead() {
        val saved = AuthorTest.entity().save()

        // --------------------------------------

        // the saved entity should be acquired by calling read API
        val response = mockMvc.perform(MockMvcRequestBuilders.get("$BASE_API/${saved.id().value}").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        assertEquals(json.getValue("name"), saved.name.value)
        assertEquals(json.getValue("biography"), saved.biography.value)
    }

    /**
     * test Update Api
     */
    @Test
    fun testUpdate() {
        val saved = AuthorTest.entity().save()
        val entity2 = AuthorTest.entity2()

        // --------------------------------------

        val json = Gson().toJson(
                mapOf(
                        "name" to entity2.name.value,
                        "biography" to entity2.biography.value)
        )

        // --------------------------------------

        // the "saved" created above should be updated with "entity" properties by calling update API
        mockMvc.perform(MockMvcRequestBuilders.put("$BASE_API/${saved.id().value}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be updated successfully
        val updated = AuthorRepository.findById(saved.id())
        assertEquals(updated.name, entity2.name)
        assertEquals(updated.biography, entity2.biography)
    }

    /**
     * test Delete Api
     */
    @Test
    fun testDelete() {
        val saved = AuthorTest.entity().save()

        // --------------------------------------

        // the entity created above should be deleted with by calling delete API
        mockMvc.perform(MockMvcRequestBuilders.delete("$BASE_API/${saved.id().value}"))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be deleted successfully
        assertThrows<NoSuchElementException> {
            AuthorRepository.findById(saved.id())
        }
    }
}