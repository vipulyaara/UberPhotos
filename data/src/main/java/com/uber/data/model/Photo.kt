package com.uber.data.model

import java.util.HashMap

data class Photo(
    var id: String? = null,
    var owner: String? = null,
    var secret: String? = null,
    var server: String? = null,
    var farm: Int? = null,
    var title: String? = null,
    var isPublic: Int? = null,
    var isFriend: Int? = null,
    var isFamily: Int? = null,
    val additionalProperties: HashMap<String, Any> = hashMapOf()
)
