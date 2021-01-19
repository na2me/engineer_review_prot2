package com.spring_boot.book

import com.google.gson.GsonBuilder
import org.aspectj.lang.annotation.Before
import org.junit.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames.FqNames.target


@SpringBootTest
class BookControllerTest() {

    lateinit var  mockMvc: MockMvc

    @Autowired
    lateinit var controller: BookController

    @Autowired
    lateinit var repository: BookRepository

    @BeforeEach
    // todo: make it as ALL
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun testIndex() {
        val book = Book("test title", 1, 2.0, "https://test.com")
        val gson = GsonBuilder().setPrettyPrinting().create()

        repository.save(book)


//        mockMvc.perform(post("/api/book/")
//                .param("title", book.title)
//                .param("category", book.category.toString())
//                .param("score", book.score.toString())
//                .param("url", book.url))
//                .andExpect(status().isOk)


        mockMvc.perform(get("/api/book/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andDo { println(it.response.contentAsString) }
//                .andExpect(content().json(json))


    }
}