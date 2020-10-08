package com.enigmaworks.stackoverflowdata.network

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    var accepted_answer_id: Int = 0,
    var answer_count: Int = 0,
    var closed_date: Int = 0,
    var closed_reason: String = "",
    var content_license: String = "",
    var creation_date: Int = 0,
    var is_answered: Boolean = false,
    var last_activity_date: Int = 0,
    var last_edit_date: Int = 0,
    var link: String = "",
    var owner: Owner = Owner(),
    var question_id: Int = 0,
    var score: Int = 0,
    var tags: List<String> = listOf(),
    var title: String = "",
    var view_count: Int = 0
)