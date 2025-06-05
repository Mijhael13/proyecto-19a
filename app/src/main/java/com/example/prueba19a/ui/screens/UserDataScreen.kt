package com.example.prueba19a.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.prueba19a.models.UserProfile
import com.example.prueba19a.utils.UserPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataScreen(
    onDataSubmitted: (UserProfile, String) -> Unit // <-- Recibe también la lesión
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val userProfile = userPreferences.getUserProfile()

    var name by remember { mutableStateOf(userProfile?.name ?: "") }
    var age by remember { mutableStateOf(if (userProfile?.age != null && userProfile.age != 0) userProfile.age.toString() else "") }
    var fitnessLevel by remember { mutableStateOf("") }
    var mainGoal by remember { mutableStateOf(userProfile?.mainGoal ?: "") }
    var motivation by remember { mutableStateOf(userProfile?.motivation ?: "") }
    var activityLevel by remember { mutableStateOf(userProfile?.activityLevel ?: "") }
    var weight by remember { mutableStateOf(if (userProfile?.weight != null && userProfile.weight != 0f) userProfile.weight.toString() else "") }
    var height by remember { mutableStateOf(if (userProfile?.height != null && userProfile.height != 0f) userProfile.height.toString() else "") }
    var expandedMotivation by remember { mutableStateOf(false) }
    var expandedActivityLevel by remember { mutableStateOf(false) }
    var profileMenuExpanded by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }

    val fitnessLevels = listOf("Principiante", "Intermedio", "Avanzado")
    val mainGoals = listOf("Pérdida de peso", "Aumento de masa muscular", "Mejora de resistencia")
    val activityLevels = listOf("Sedentario", "Moderado", "Activo")
    val objetivos = listOf("Bajar peso", "Aumentar músculo", "Mantenerse en forma")

    var objetivo by remember { mutableStateOf(userProfile?.motivation ?: "") }
    var expandedObjetivo by remember { mutableStateOf(false) }
    var expandedFitnessLevel by remember { mutableStateOf(false) }
    var expandedMainGoalCombo by remember { mutableStateOf(false) }
    var expandedActivityLevelCombo by remember { mutableStateOf(false) }

    var lesion by remember { mutableStateOf(userProfile?.lesion ?: "") }
    var expandedLesion by remember { mutableStateOf(false) }
    val opcionesLesion = listOf(
        "No",
        "Esguince",
        "Luxaciones",
        "Lesiones de meniscos"
    )

    var enfermedad by remember { mutableStateOf(userProfile?.enfermedad ?: "") }
    var expandedEnfermedad by remember { mutableStateOf(false) }
    val opcionesEnfermedad = listOf(
        "No",
        "Obesidad"
    )

    var dificultadMedica by remember { mutableStateOf(userProfile?.dificultadMedica ?: "") }
    var expandedDificultadMedica by remember { mutableStateOf(false) }
    val opcionesDificultadMedica = listOf(
        "No",
        "Tendinitis",
        "Problemas de espalda"
    )

    fun updateScore() {
        score = 0
        if (fitnessLevel == "Intermedio") score += 2
        if (fitnessLevel == "Avanzado") score += 3
        if (mainGoal == "Aumento de masa muscular") score += 2
        if (mainGoal == "Mejora de resistencia") score += 1
        if (objetivo == "Aumentar músculo") score += 2
        if (objetivo == "Bajar peso") score += 1 // Cambiado de "Aumentar peso" a "Bajar peso"
        if (objetivo == "Mantenerse en forma") score += 1
        // Puntaje por nivel de actividad
        score += when (activityLevel) {
            "Sedentario" -> 0
            "Moderado" -> 3
            "Activo" -> 5
            else -> 0
        }
        // Puntaje por lesión
        score += when (lesion) {
            "No" -> 1
            else -> 0
        }
        // Puntaje por enfermedad
        score += when (enfermedad) {
            "No" -> 1
            else -> 0
        }
        // Puntaje por dificultad médica
        score += when (dificultadMedica) {
            "No" -> 1
            else -> 0
        }
        // Nuevo cálculo de puntaje por peso SOLO si el campo no está vacío y es válido
        val peso = weight.toFloatOrNull()
        if (peso != null) {
            score += when {
                peso <= 49f -> 1
                peso in 50f..65f -> 3
                peso in 66f..90f -> 2
                peso >= 91f -> 1
                else -> 0
            }
        }
        // Nuevo cálculo de puntaje por altura
        val altura = height.toFloatOrNull() ?: 0f
        if (altura >= 171f) score += 1

        // Puntaje por edad
        val edadInt = age.toIntOrNull() ?: 0
        if (edadInt >= 16) score += 1
    }

    LaunchedEffect(fitnessLevel, mainGoal, objetivo, activityLevel, weight, height, lesion, enfermedad, dificultadMedica) {
        updateScore()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 24.dp)
            .background(MaterialTheme.colorScheme.surface),
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
            onValueChange = {
                age = it
                updateScore() // Actualiza el puntaje inmediatamente al cambiar la edad
            },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Eliminar ComboBox para nivel de condición física

        // ComboBox para objetivo personalizado
        Text("¿Cuál es tu objetivo?")
        ExposedDropdownMenuBox(
            expanded = expandedObjetivo,
            onExpandedChange = { expandedObjetivo = !expandedObjetivo }
        ) {
            TextField(
                value = if (objetivo.isEmpty()) "Selecciona tu objetivo" else objetivo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Objetivo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedObjetivo) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedObjetivo,
                onDismissRequest = { expandedObjetivo = false }
            ) {
                objetivos.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            objetivo = option
                            expandedObjetivo = false
                        },
                        modifier = if (objetivo == option) Modifier.background(Color(0xFFE0E0E0)) else Modifier
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // ComboBox para nivel de actividad
        Text("Nivel de actividad")
        ExposedDropdownMenuBox(
            expanded = expandedActivityLevelCombo,
            onExpandedChange = { expandedActivityLevelCombo = !expandedActivityLevelCombo }
        ) {
            TextField(
                value = if (activityLevel.isEmpty()) "Selecciona el nivel de actividad" else activityLevel,
                onValueChange = {},
                readOnly = true,
                label = { Text("Nivel de actividad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedActivityLevelCombo) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedActivityLevelCombo,
                onDismissRequest = { expandedActivityLevelCombo = false }
            ) {
                activityLevels.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            activityLevel = option
                            expandedActivityLevelCombo = false
                        },
                        modifier = if (activityLevel == option) Modifier.background(Color(0xFFE0E0E0)) else Modifier
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Eliminar el apartado de los días disponibles

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
        Spacer(modifier = Modifier.height(8.dp))

        // ComboBox para lesión
        Text("¿Posees una lesión?")
        ExposedDropdownMenuBox(
            expanded = expandedLesion,
            onExpandedChange = { expandedLesion = !expandedLesion }
        ) {
            TextField(
                value = if (lesion.isEmpty()) "Selecciona una opción" else lesion,
                onValueChange = {},
                readOnly = true,
                label = { Text("Lesión") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLesion) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedLesion,
                onDismissRequest = { expandedLesion = false }
            ) {
                opcionesLesion.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            lesion = option
                            expandedLesion = false
                        },
                        modifier = if (lesion == option) Modifier.background(Color(0xFFE0E0E0)) else Modifier
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // ComboBox para enfermedad
        Text("¿Posees alguna enfermedad?")
        ExposedDropdownMenuBox(
            expanded = expandedEnfermedad,
            onExpandedChange = { expandedEnfermedad = !expandedEnfermedad }
        ) {
            TextField(
                value = if (enfermedad.isEmpty()) "Selecciona una opción" else enfermedad,
                onValueChange = {},
                readOnly = true,
                label = { Text("Enfermedad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEnfermedad) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedEnfermedad,
                onDismissRequest = { expandedEnfermedad = false }
            ) {
                opcionesEnfermedad.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            enfermedad = option
                            expandedEnfermedad = false
                        },
                        modifier = if (enfermedad == option) Modifier.background(Color(0xFFE0E0E0)) else Modifier
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // ComboBox para dificultad médica
        Text("¿Posees alguna dificultad médica?")
        ExposedDropdownMenuBox(
            expanded = expandedDificultadMedica,
            onExpandedChange = { expandedDificultadMedica = !expandedDificultadMedica }
        ) {
            TextField(
                value = if (dificultadMedica.isEmpty()) "Selecciona una opción" else dificultadMedica,
                onValueChange = {},
                readOnly = true,
                label = { Text("Dificultad médica") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDificultadMedica) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedDificultadMedica,
                onDismissRequest = { expandedDificultadMedica = false }
            ) {
                opcionesDificultadMedica.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            dificultadMedica = option
                            expandedDificultadMedica = false
                        },
                        modifier = if (dificultadMedica == option) Modifier.background(Color(0xFFE0E0E0)) else Modifier
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val userProfile = UserProfile(
                    name = name,
                    age = age.toIntOrNull() ?: 0,
                    sex = "",
                    muscleGroup = "",
                    mainGoal = mainGoal,
                    motivation = objetivo,
                    activityLevel = activityLevel,
                    weight = weight.toFloatOrNull() ?: 0f,
                    height = height.toFloatOrNull() ?: 0f,
                    availableDays = emptyList(), // Ahora siempre vacío
                    score = score,
                    lesion = lesion,
                    enfermedad = enfermedad,
                    dificultadMedica = dificultadMedica
                )
                userPreferences.saveUserProfile(userProfile)
                onDataSubmitted(userProfile, lesion) // <-- Pasar lesion al callback
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Enviar", color = Color.White)
        }
    }
}
