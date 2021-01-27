package com.spring_boot.base.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

private var context: ApplicationContext? = null

/** resolve container */
fun <T> resolve(clazz: Class<T>): T {
    val bean = context?.getBean(clazz)
    if (bean != null) {
        return bean
    }
    throw IllegalStateException("Spring utility class not initialized.")
}

/** DI container resolver */
@Component
class Resolver : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}