package com.example.prueba19a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.prueba19a.ui.screens.UserDataScreen
import com.example.prueba19a.ui.screens.RecommendedExercisesScreen
import com.example.prueba19a.ui.theme.Prueba19aTheme
import com.example.prueba19a.utils.UserPreferences

class MainActivity : ComponentActivity() {
    // Variables para almacenar los datos del usuario
    private var name by mutableStateOf("")
    private var age by mutableStateOf(0)
    private var sex by mutableStateOf("")
    private var muscleGroup by mutableStateOf("")
    private var mainGoal by mutableStateOf("")
    private var motivation by mutableStateOf("")
    private var activityLevel by mutableStateOf("")
    private var weight by mutableStateOf(0f)
    private var height by mutableStateOf(0f)
    private var recommendedExercises by mutableStateOf("")
    private var score by mutableStateOf(0) // Nuevo: puntaje acumulado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prueba19aTheme {
                var currentScreen by remember { mutableStateOf("userData") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "userData" -> {
                            UserDataScreen(
                                onDataSubmitted = { profile ->
                                    // Actualiza los datos del usuario en MainActivity
                                    name = profile.name
                                    age = profile.age
                                    sex = profile.sex
                                    muscleGroup = profile.muscleGroup
                                    mainGoal = profile.mainGoal
                                    motivation = profile.motivation
                                    activityLevel = profile.activityLevel
                                    weight = profile.weight
                                    height = profile.height
                                    score = profile.score // Guardar puntaje
                                    recommendedExercises = recommendExercises(profile.score)
                                    currentScreen = "recommendedExercises"
                                }
                            )
                        }
                        "recommendedExercises" -> {
                            RecommendedExercisesScreen(
                                exercises = recommendedExercises,
                                userName = name,
                                userAge = age,
                                userSex = sex,
                                userMuscleGroup = muscleGroup,
                                userMainGoal = mainGoal,
                                userMotivation = motivation,
                                userActivityLevel = activityLevel,
                                userWeight = weight,
                                userHeight = height,
                                score = score, // Pasar puntaje
                                onBack = { currentScreen = "userData" }
                            )
                        }
                    }
                }
            }
        }
    }

    // Ahora recibe el puntaje y retorna la rutina según el score
    private fun recommendExercises(score: Int): String {
        return when {
            score >= 10 -> {
                "Rutina avanzada:\n1. Dominadas\n2. Sentadillas con salto\n3. Burpees\n4. HIIT"
            }
            score in 6..9 -> {
                "Rutina intermedia:\n1. Flexiones\n2. Sentadillas\n3. Abdominales\n4. Cardio moderado"
            }
            else -> {
                "Rutina principiante:\n1. Caminata\n2. Sentadillas asistidas\n3. Plancha\n4. Estiramientos"
            }
        }
    }
}