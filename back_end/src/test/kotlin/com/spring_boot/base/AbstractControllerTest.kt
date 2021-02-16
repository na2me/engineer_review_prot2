package com.spring_boot.base

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
abstract class AbstractControllerTest<T : Any> {

    // API base URL
    lateinit var BASE_API: String

    @Autowired
    lateinit var controller: T

    lateinit var mockMvc: MockMvc

    /**
     * setup mockMvc DI for each test methods
     */
    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }
}