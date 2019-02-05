package com.uber.data.model

import java.util.HashMap

data class Photos(
    var page: Int? = null,
    var pages: String? = null,
    var perPage: Int? = null,
    var total: String? = null,
    var photo: List<Photo>? = null,
    val additionalProperties: HashMap<String, Any> = hashMapOf()
)
