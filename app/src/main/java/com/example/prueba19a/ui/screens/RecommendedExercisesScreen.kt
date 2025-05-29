package com.example.prueba19a.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person

@Composable
fun RecommendedExercisesScreen(
    exercises: String,
    userName: String,
    userAge: Int,
    userSex: String,
    userMuscleGroup: String,
    userMainGoal: String,
    userMotivation: String,
    userActivityLevel: String,
    userWeight: Float,
    userHeight: Float,
    onBack: () -> Unit
) {
    // Estado para controlar la visibilidad de los datos del usuario
    var showUserProfile by remember { mutableStateOf(false) }

    // Tabs para grupos musculares
    val muscleTabs = listOf("Pierna", "Brazos", "Cardio", "Pecho", "Abdomen")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón de perfil
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { showUserProfile = !showUserProfile }) {
                Icon(Icons.Default.Person, contentDescription = "Perfil")
            }
        }

        Text(text = "Ejercicios Recomendados", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Menú de navegación de grupos musculares
        TabRow(selectedTabIndex = selectedTabIndex) {
            muscleTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        // Contenido de la rutina según la pestaña seleccionada
        Text(getRoutine(muscleTabs[selectedTabIndex], userMainGoal), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar información del perfil del usuario solo si showUserProfile es true
        if (showUserProfile) {
            Text("Perfil del Usuario", style = MaterialTheme.typography.titleMedium)
            Text("Nombre: $userName")
            Text("Edad: $userAge")
            Text("Sexo: $userSex")
            Text("Grupo muscular: $userMuscleGroup")
            Text("Objetivo: $userMainGoal")
            Text("Motivación: $userMotivation")
            Text("Nivel de actividad: $userActivityLevel")
            Text("Peso: $userWeight kg")
            Text("Altura: $userHeight cm")
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Cambiar el texto del botón según la visibilidad del perfil
        Button(onClick = onBack) {
            Text(if (showUserProfile) "Modificar Datos" else "Volver")
        }
    }
}

// Función para obtener la rutina según el grupo muscular y el puntaje
fun getRoutine(muscleGroup: String, score: String): String {
    return when (muscleGroup) {
        "Pierna" -> """
Principiante

Sentadillas
3 series de 10-12 reps

Zancadas
3 series de 8-10 reps por pierna

Puente de Glúteos
3 series de 12-15 reps

Saltos bipodales
3 series de 15-20 reps

Elevación de talones
3 series de 15-20 reps
""".trimIndent()
        else -> "Rutina de $muscleGroup con puntaje $score"
    }
}
