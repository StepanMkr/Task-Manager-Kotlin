package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val title: String,
    val completed: Boolean = false
)