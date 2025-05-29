package com.example.prueba19a.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.background
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.rememberScrollState

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
    lesion: String = "", // Valor por defecto para evitar error si no se pasa
    onBack: () -> Unit
) {
    // Estado para controlar la visibilidad de los datos del usuario
    var showUserProfile by remember { mutableStateOf(false) }

    // Tabs para grupos musculares
    val baseMuscleTabs = listOf("Pierna", "Brazos", "Cardio", "Pecho", "Abdominales")
    val muscleTabs = if (lesion == "Esguince") baseMuscleTabs + "Lesiones" else baseMuscleTabs
    var selectedTabIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 24.dp) // Márgenes más amplios
            .background(MaterialTheme.colorScheme.surface), // Elimina clip para evitar el error
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

        Text(text = "Ejercicios Recomendados", style = MaterialTheme.typography.titleLarge.copy(color = Color.Black))
        Spacer(modifier = Modifier.height(16.dp))

        // Menú de navegación de grupos musculares
        ScrollableTabRow(selectedTabIndex = selectedTabIndex, contentColor = Color(0xFF1A98B8)) {
            muscleTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, color = if (selectedTabIndex == index) Color(0xFF1A98B8) else LocalContentColor.current) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Padding entre tabs y contenido
        // Contenido de la rutina según la pestaña seleccionada
        if (muscleTabs[selectedTabIndex] == "Lesiones") {
            Text(
                """
Ejercicios para la Recuperación de Esguinces

1. Movilidad Articular
Descripción: Realiza movimientos suaves para mejorar la movilidad.
Ejercicio: Si es un esguince de tobillo, mueve el pie en círculos en ambas direcciones. Haz flexiones y extensiones del tobillo.

2. Estiramientos
Descripción: Estira suavemente los músculos alrededor de la articulación afectada.
Ejercicio: Para un esguince de tobillo, siéntate y estira la pierna afectada. Tira suavemente de los dedos del pie hacia ti para estirar la parte posterior de la pierna.

3. Fortalecimiento Isométrico
Descripción: Fortalecer los músculos sin mover la articulación.
Ejercicio: Presiona suavemente el pie contra una pared o una almohada sin moverlo. Mantén la presión durante 5-10 segundos y relaja.

4. Elevación de Talones
Descripción: Fortalecer los músculos de la pantorrilla.
Ejercicio: De pie, levanta lentamente los talones del suelo y mantenlos en esa posición durante unos segundos. Luego, baja lentamente.

5. Ejercicio de Equilibrio
Descripción: Mejora la estabilidad.
Ejercicio: Párate sobre una pierna (la no afectada) durante 10-30 segundos. Si te sientes seguro, intenta hacerlo con los ojos cerrados.
""".trimIndent(),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            Text(getRoutine(muscleTabs[selectedTabIndex], userMainGoal), style = MaterialTheme.typography.bodyLarge)
        }
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
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A98B8)),
            modifier = Modifier.fillMaxWidth()
        ) {
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

@Preview(showBackground = true)
@Composable
fun RecommendedExercisesScreenPreview() {
    RecommendedExercisesScreen(
        exercises = "",
        userName = "Juan Pérez",
        userAge = 28,
        userSex = "Masculino",
        userMuscleGroup = "Pierna",
        userMainGoal = "Principiante",
        userMotivation = "Bajar de peso",
        userActivityLevel = "Sedentario",
        userWeight = 70f,
        userHeight = 175f,
        onBack = {}
    )
}
