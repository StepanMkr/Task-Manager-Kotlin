package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskRequest(
    val title: String? = null,
    val completed: Boolean? = null
)
