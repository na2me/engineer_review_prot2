package com.spring_boot.base.util.http

/**
 * Request Parameters passed through API call
 */
class RequestParams(private val params: Map<String, String>) {
    /**
     * @return string value associated with [key]
     */
    fun getValue(key: String) =
            params[key] ?: throw IllegalArgumentException("no value associated with the key:$key")
}