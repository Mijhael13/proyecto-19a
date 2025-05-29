package com.example.prueba19a.models

data class UserProfile(
    val name: String,
    val age: Int,
    val sex: String,
    val muscleGroup: String,
    val mainGoal: String,
    val motivation: String,
    val activityLevel: String,
    val weight: Float,
    val height: Float,
    val availableDays: List<String>,
    val score: Int = 0 // Asegura que el parámetro score tenga valor por defecto
)
