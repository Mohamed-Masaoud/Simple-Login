package com.example.simpleregister.Model

data class UserProfile(
    val name: String = "",
    val bio: String = "",
    val skills: List<String> = emptyList()
)
