package com.example.prueba19a.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.prueba19a.models.UserProfile
import com.example.prueba19a.utils.UserPreferences
import androidx.compose.ui.platform.LocalContext

@Composable
fun UserDataScreen(
    onDataSubmitted: (UserProfile) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var fitnessLevel by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var muscleGroup by remember { mutableStateOf("") }
    var mainGoal by remember { mutableStateOf("") }
    var motivation by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var availableDays by remember { mutableStateOf(listOf<String>()) }
    var expandedSex by remember { mutableStateOf(false) }
    var expandedMuscleGroup by remember { mutableStateOf(false) }
    var expandedMainGoal by remember { mutableStateOf(false) }
    var expandedMotivation by remember { mutableStateOf(false) }
    var expandedActivityLevel by remember { mutableStateOf(false) }
    var profileMenuExpanded by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) } // Nuevo: puntaje acumulado

    val fitnessLevels = listOf("Principiante", "Intermedio", "Avanzado")
    val sexes = listOf("Masculino", "Femenino", "Otro")
    val muscleGroups = listOf("Pecho", "Espalda", "Piernas", "Hombros", "Brazos", "Abdomen")
    val mainGoals = listOf("Pérdida de peso", "Aumento de masa muscular", "Mejora de resistencia")
    val motivations = listOf("Liberar estrés", "Mejorar salud", "Confianza en mí")
    val activityLevels = listOf("Sedentario", "Moderado", "Activo")
    val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

    val context = LocalContext.current
    val userPreferences = UserPreferences(context)

    // Ejemplo de puntaje por selección
    // Puedes ajustar los valores según la importancia de cada pregunta
    fun updateScore() {
        score = 0
        if (fitnessLevel == "Intermedio") score += 2
        if (fitnessLevel == "Avanzado") score += 3
        if (mainGoal == "Aumento de masa muscular") score += 2
        if (mainGoal == "Mejora de resistencia") score += 1
        if (motivation == "Mejorar salud") score += 1
        if (activityLevel == "Activo") score += 2
        if (weight.toFloatOrNull() ?: 0f > 80) score += 1
        if (height.toFloatOrNull() ?: 0f > 170) score += 1
        score += availableDays.size // 1 punto por cada día disponible
    }

    // Llama a updateScore cada vez que cambie una respuesta relevante
    LaunchedEffect(fitnessLevel, mainGoal, motivation, activityLevel, weight, height, availableDays) {
        updateScore()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Menú desplegable para el sexo
        OutlinedButton(onClick = { expandedSex = true }) {
            Text(text = if (sex.isEmpty()) "Selecciona el sexo" else sex)
        }
        DropdownMenu(
            expanded = expandedSex,
            onDismissRequest = { expandedSex = false }
        ) {
            sexes.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        sex = option
                        expandedSex = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Menú desplegable para el grupo muscular
        OutlinedButton(onClick = { expandedMuscleGroup = true }) {
            Text(text = if (muscleGroup.isEmpty()) "Selecciona el grupo muscular" else muscleGroup)
        }
        DropdownMenu(
            expanded = expandedMuscleGroup,
            onDismissRequest = { expandedMuscleGroup = false }
        ) {
            muscleGroups.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        muscleGroup = option
                        expandedMuscleGroup = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Menú desplegable para el objetivo principal
        OutlinedButton(onClick = { expandedMainGoal = true }) {
            Text(text = if (mainGoal.isEmpty()) "Selecciona el objetivo principal" else mainGoal)
        }
        DropdownMenu(
            expanded = expandedMainGoal,
            onDismissRequest = { expandedMainGoal = false }
        ) {
            mainGoals.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        mainGoal = option
                        expandedMainGoal = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Menú desplegable para la motivación
        OutlinedButton(onClick = { expandedMotivation = true }) {
            Text(text = if (motivation.isEmpty()) "Selecciona la motivación" else motivation)
        }
        DropdownMenu(
            expanded = expandedMotivation,
            onDismissRequest = { expandedMotivation = false }
        ) {
            motivations.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        motivation = option
                        expandedMotivation = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Menú desplegable para el nivel de actividad
        OutlinedButton(onClick = { expandedActivityLevel = true }) {
            Text(text = if (activityLevel.isEmpty()) "Selecciona el nivel de actividad" else activityLevel)
        }
        DropdownMenu(
            expanded = expandedActivityLevel,
            onDismissRequest = { expandedActivityLevel = false }
        ) {
            activityLevels.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        activityLevel = option
                        expandedActivityLevel = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Checkbox para los días disponibles
        Text("Días disponibles para objetivos:")
        daysOfWeek.forEach { day ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = availableDays.contains(day),
                    onCheckedChange = {
                        if (it) {
                            availableDays = availableDays + day
                        } else {
                            availableDays = availableDays - day
                        }
                    }
                )
                Text(day)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Altura (cm)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Puntaje acumulado: $score", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val userProfile = UserProfile(
                name = name,
                age = age.toIntOrNull() ?: 0,
                sex = sex,
                muscleGroup = muscleGroup,
                mainGoal = mainGoal,
                motivation = motivation,
                activityLevel = activityLevel,
                weight = weight.toFloatOrNull() ?: 0f,
                height = height.toFloatOrNull() ?: 0f,
                availableDays = availableDays
            )
            userPreferences.saveUserProfile(userProfile)
            onDataSubmitted(userProfile.copy(score = score)) // Pasar el puntaje
        }) {
            Text("Enviar")
        }
    }
}
