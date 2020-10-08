package com.ascensia.stackoverflowdata.network

import kotlinx.serialization.Serializable

@Serializable
data class StackResponse(
    var has_more: Boolean = false,
    var items: List<Item> = listOf(),
    var quota_max: Int = 0,
    var quota_remaining: Int = 0
)