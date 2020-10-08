package com.enigmaworks.stackoverflowdata.network

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    var accept_rate: Int = 0,
    var display_name: String = "",
    var link: String = "",
    var profile_image: String = "",
    var reputation: Int = 0,
    var user_id: Int = 0,
    var user_type: String = ""
)