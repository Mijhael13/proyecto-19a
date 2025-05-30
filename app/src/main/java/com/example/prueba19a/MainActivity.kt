package com.example.prueba19a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.prueba19a.ui.screens.LoginScreen
import com.example.prueba19a.ui.screens.UserDataScreen
import com.example.prueba19a.ui.screens.RecommendedExercisesScreen
import com.example.prueba19a.ui.theme.Prueba19aTheme
import com.example.prueba19a.utils.UserPreferences

class MainActivity : ComponentActivity() {
    private val validUsername = "testUser"
    private val validPassword = "testPassword"

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
    private var lesion by mutableStateOf("") // <-- Agregar variable de lesión
    private var enfermedad by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prueba19aTheme {
                var currentScreen by remember { mutableStateOf("login") }
                var recommendedExercises by remember { mutableStateOf("") }

                val userPreferences = UserPreferences(this)
                val userProfile = userPreferences.getUserProfile()

                if (userProfile != null) {
                    // Si hay un perfil de usuario guardado, usa esos datos
                    name = userProfile.name
                    age = userProfile.age
                    sex = userProfile.sex
                    muscleGroup = userProfile.muscleGroup
                    mainGoal = userProfile.mainGoal
                    motivation = userProfile.motivation
                    activityLevel = userProfile.activityLevel
                    weight = userProfile.weight
                    height = userProfile.height
                    lesion = userProfile.lesion // <-- Obtener lesión del perfil guardado
                    enfermedad = userProfile.enfermedad
                    currentScreen = "recommendedExercises"
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "userData" -> {
                            UserDataScreen(
                                onDataSubmitted = { profile, lesion ->
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
                                    this.lesion = lesion // Guardar la lesión seleccionada
                                    this.enfermedad = profile.enfermedad
                                    recommendedExercises = recommendExercises(profile.mainGoal)
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
                                lesion = lesion,
                                enfermedad = enfermedad,
                                onBack = { currentScreen = "userData" }
                            )
                        }
                        else -> {
                            LoginScreen(
                                onLoginClick = { username, password ->
                                    if (username == validUsername && password == validPassword) {
                                        currentScreen = "userData"
                                    } else {
                                        // Aquí puedes mostrar un mensaje de error
                                        println("Credenciales incorrectas")
                                    }
                                },
                                onNavigateToUserData = { /* No se necesita aquí */ }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun recommendExercises(fitnessLevel: String): String {
        return when (fitnessLevel.lowercase()) {
            "principiante" -> {
                "Ejercicios recomendados para principiantes:\n" +
                "1. Caminata\n" +
                "2. Flexiones de rodillas\n" +
                "3. Sentadillas"
            }
            "intermedio" -> {
                "Ejercicios recomendados para intermedios:\n" +
                "1. Correr\n" +
                "2. Flexiones\n" +
                "3. Elevaciones de talones"
            }
            "avanzado" -> {
                "Ejercicios recomendados para avanzados:\n" +
                "1. Entrenamiento de intervalos\n" +
                "2. Dominadas\n" +
                "3. Burpees"
            }
            else -> {
                // Ejercicios de prueba basados en las variables del perfil
                "Ejercicios recomendados:\n" +
                "1. Saltos de tijera (para mejorar la resistencia)\n" +
                "2. Abdominales (para fortalecer el abdomen)\n" +
                "3. Plancha (para mejorar la estabilidad)"
            }
        }
    }
}