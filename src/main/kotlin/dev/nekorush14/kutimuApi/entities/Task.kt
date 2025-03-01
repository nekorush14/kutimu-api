package dev.nekorush14.kutimuApi.entities

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val name: String,
    val description: String?,
    val completed: Boolean,
    val icon: String?,
)
