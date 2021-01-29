package com.spring_boot.base.util.http

class RequestParams(private val params: Map<String, String>) {
    fun getValue(key: String) =
    params[key] ?: throw IllegalArgumentException()
}