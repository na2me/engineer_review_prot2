package com.spring_boot.domain.account.controller

import com.spring_boot.base.AbstractControllerTest
import com.spring_boot.base.util.json.getCollectionElements
import com.spring_boot.base.util.json.getValue
import com.spring_boot.base.util.security.passwordEncoder
import com.spring_boot.domain.account.AccountTest
import com.spring_boot.domain.account.repository.AccountRepository
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

class AccountControllerTest : AbstractControllerTest<AccountController>() {

    override var BASE_API = "/api/account/"

    /**
     * test Index Api
     */
    @Test
    fun testIndex() {
        AccountTest.entity().save()

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
        val entity = AccountTest.entity()

        // --------------------------------------

        // the entity created above should be returned as saved one by calling store API
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_API)
                .param("name", entity.name.value)
                .param("email", entity.email.value)
                .param("password", entity.password.value))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))

        // --------------------------------------

        // the entity should be saved successfully
        val entities = AccountRepository.findAll()
        entities.contains(entity)

        // --------------------------------------

        // the password should be encrypted
        assertTrue(passwordEncoder().matches(
                entity.password.value, entities.first().password.value))
    }

    /**
     * test Read Api
     */
    @Test
    fun testRead() {
        val saved = AccountTest.entity().save()

        // --------------------------------------

        // the saved entity should be acquired by calling read API
        val response = mockMvc.perform(MockMvcRequestBuilders.get("$BASE_API${saved.id().value}").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        assertEquals(json.getValue("name"), saved.name.value)
        assertEquals(json.getValue("email"), saved.email.value)
        assertEquals(json.getValue("password"), saved.password.value)
    }

    /**
     * test Update Api
     */
    @Test
    fun testUpdate() {
        val saved = AccountTest.entity().save()
        val entity2 = AccountTest.entity2()

        // --------------------------------------

        // the "saved" created above should be updated with "entity" properties by calling update API
        mockMvc.perform(MockMvcRequestBuilders.post("$BASE_API${saved.id().value}")
                .param("name", entity2.name.value)
                .param("email", entity2.email.value)
                .param("password", entity2.password.value))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be updated successfully
        val updated = AccountRepository.findById(saved.id())
        assertEquals(updated.name, entity2.name)
        assertEquals(updated.email, entity2.email)
        // the password should be encrypted
        assertTrue(passwordEncoder().matches(
                entity2.password.value, updated.password.value))
    }

    /**
     * test Delete Api
     */
    @Test
    fun testDelete() {
        val saved = AccountTest.entity().save()

        // --------------------------------------

        // the entity created above should be deleted with by calling delete API
        mockMvc.perform(MockMvcRequestBuilders.delete("$BASE_API${saved.id().value}"))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be deleted successfully
        assertThrows<NoSuchElementException> {
            AccountRepository.findById(saved.id())
        }
    }
}