package com.spring_boot.base.util.json

import org.json.JSONObject

/**
 * @return string value associated with [field]
 */
fun JSONObject.getValue(field: String): Any =
        this.getJSONObject(field).get("value")

/**
 * @return id value as Long
 */
fun JSONObject.getId() = this.get("id").toString().toLong()

/**
 * @return id stored as foreign key of associated entity
 */
fun JSONObject.getForeignKeyOf(entity: String) =
        this.getJSONObject(entity).getId()

/**
 * @return JSONArray containing elements
 */
fun JSONObject.getCollectionElements() =
        this.getJSONArray("list")
