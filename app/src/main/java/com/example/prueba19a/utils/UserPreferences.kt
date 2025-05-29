package com.example.prueba19a.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.prueba19a.models.UserProfile

class UserPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Método para guardar el perfil del usuario
    fun saveUserProfile(userProfile: UserProfile) {
        with(sharedPreferences.edit()) {
            putString("name", userProfile.name)
            putInt("age", userProfile.age)
            putString("sex", userProfile.sex)
            putString("muscleGroup", userProfile.muscleGroup)
            putString("mainGoal", userProfile.mainGoal)
            putString("motivation", userProfile.motivation)
            putString("activityLevel", userProfile.activityLevel)
            putFloat("weight", userProfile.weight)
            putFloat("height", userProfile.height)
            putStringSet("availableDays", userProfile.availableDays.toSet())
            putInt("score", userProfile.score) // Guardar puntaje
            apply() // Guardar los cambios
        }
    }

    // Método para recuperar el perfil del usuario
    fun getUserProfile(): UserProfile? {
        val name = sharedPreferences.getString("name", null) ?: return null
        val age = sharedPreferences.getInt("age", 0)
        val sex = sharedPreferences.getString("sex", "") ?: ""
        val muscleGroup = sharedPreferences.getString("muscleGroup", "") ?: ""
        val mainGoal = sharedPreferences.getString("mainGoal", "") ?: ""
        val motivation = sharedPreferences.getString("motivation", "") ?: ""
        val activityLevel = sharedPreferences.getString("activityLevel", "") ?: ""
        val weight = sharedPreferences.getFloat("weight", 0f)
        val height = sharedPreferences.getFloat("height", 0f)
        val availableDays = sharedPreferences.getStringSet("availableDays", emptySet())?.toList() ?: emptyList()
        val score = sharedPreferences.getInt("score", 0) // Leer puntaje

        return UserProfile(name, age, sex, muscleGroup, mainGoal, motivation, activityLevel, weight, height, availableDays, score)
    }
}
