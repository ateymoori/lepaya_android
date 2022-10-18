package com.lepaya.domain.entities

data class TrainerEntity(
    var picture: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var full_name: String? = null,
    var about: String? = null,
    var email: String? = null,
    var isAvailable: Boolean? = null,
    var favoriteFruit: String? = null,
    var tags: List<String>? = null
)
