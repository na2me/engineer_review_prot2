package com.spring_boot.base.util.json

import org.json.JSONObject

/**
 * @return string value associated with [field]
 */
fun JSONObject.getValue(field: String) =
        this.getJSONObject(field).get("value")
