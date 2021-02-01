package com.spring_boot.model.account.controller

import com.spring_boot.base.AbstractControllerTest
import com.spring_boot.base.util.json.getValue
import com.spring_boot.model.account.AccountTest
import com.spring_boot.model.account.repository.AccountRepository
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert
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
        val saved = AccountRepository.save(AccountTest.entity())

        // --------------------------------------

        // the saved entity should be acquired by calling index API
        val response = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONArray(response).getJSONObject(0)
        Assert.assertEquals(json.getValue("name"), saved.name.value)
        Assert.assertEquals(json.getValue("email"), saved.email.value)
        Assert.assertEquals(json.getValue("password"), saved.password.value)
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
    }

    /**
     * test Read Api
     */
    @Test
    fun testRead() {
        val entity = AccountTest.entity()
        val saved = AccountRepository.save(entity)

        // --------------------------------------

        // the saved entity should be acquired by calling read API
        val response = mockMvc.perform(MockMvcRequestBuilders.get("$BASE_API${saved.id()}").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString

        // --------------------------------------

        // response should be the same entity as "saved"
        val json = JSONObject(response)
        Assert.assertEquals(json.getValue("name"), saved.name.value)
        Assert.assertEquals(json.getValue("email"), saved.email.value)
        Assert.assertEquals(json.getValue("password"), saved.password.value)
    }

    /**
     * test Update Api
     */
    @Test
    fun testUpdate() {
        val saved = AccountRepository.save(AccountTest.entity())
        val entity = AccountTest.entity2()

        // --------------------------------------

        // the "saved" created above should be updated with "entity" properties by calling update API
        mockMvc.perform(MockMvcRequestBuilders.post("$BASE_API${saved.id()}")
                .param("name", entity.name.value)
                .param("email", entity.email.value)
                .param("password", entity.password.value))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be updated successfully
        val updated = AccountRepository.findById(saved.id())
        Assert.assertEquals(updated.name, entity.name)
        Assert.assertEquals(updated.email, entity.email)
        Assert.assertEquals(updated.password, entity.password)
    }

    /**
     * test Delete Api
     */
    @Test
    fun testDelete() {
        val entity = AccountTest.entity()
        val saved = AccountRepository.save(entity)

        // --------------------------------------

        // the entity created above should be deleted with by calling delete API
        mockMvc.perform(MockMvcRequestBuilders.delete("$BASE_API${saved.id()}"))
                .andExpect(MockMvcResultMatchers.status().isOk)

        // --------------------------------------

        // the entity should be deleted successfully
        assertThrows<NoSuchElementException> {
            AccountRepository.findById(saved.id())
        }
    }
}