package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val title: String
)
