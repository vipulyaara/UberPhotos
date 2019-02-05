package com.uber.data.model

import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

data class PhotosResponse(
    var photos: Photos? = null,
    var stat: String? = null,
    val additionalProperties: HashMap<String, Any> = hashMapOf()
)
