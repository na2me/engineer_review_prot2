package com.spring_boot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EngineerReviewApplication

fun main(args: Array<String>) {
    runApplication<EngineerReviewApplication>(*args)
}
