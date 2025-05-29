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
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataScreen(
    onDataSubmitted: (UserProfile) -> Unit
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var fitnessLevel by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var mainGoal by remember { mutableStateOf("") }
    var motivation by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var expandedSex by remember { mutableStateOf(false) }
    var expandedMotivation by remember { mutableStateOf(false) }
    var expandedActivityLevel by remember { mutableStateOf(false) }
    var profileMenuExpanded by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }

    val fitnessLevels = listOf("Principiante", "Intermedio", "Avanzado")
    val sexes = listOf("Masculino", "Femenino", "Otro")
    val mainGoals = listOf("Pérdida de peso", "Aumento de masa muscular", "Mejora de resistencia")
    val activityLevels = listOf("Sedentario", "Moderado", "Activo")
    val objetivos = listOf("Aumentar peso", "Aumentar músculo", "Mantenerse en forma")

    var objetivo by remember { mutableStateOf("") }
    var expandedObjetivo by remember { mutableStateOf(false) }
    var expandedFitnessLevel by remember { mutableStateOf(false) }
    var expandedSexCombo by remember { mutableStateOf(false) }
    var expandedMainGoalCombo by remember { mutableStateOf(false) }
    var expandedActivityLevelCombo by remember { mutableStateOf(false) }

    fun updateScore() {
        score = 0
        if (fitnessLevel == "Intermedio") score += 2
        if (fitnessLevel == "Avanzado") score += 3
        if (mainGoal == "Aumento de masa muscular") score += 2
        if (mainGoal == "Mejora de resistencia") score += 1
        if (objetivo == "Aumentar músculo") score += 2
        if (objetivo == "Aumentar peso") score += 1
        if (objetivo == "Mantenerse en forma") score += 1
        if (activityLevel == "Activo") score += 2
        if (weight.toFloatOrNull() ?: 0f > 80) score += 1
        if (height.toFloatOrNull() ?: 0f > 170) score += 1
        // score += availableDays.size // Eliminar esta línea ya que no se usa availableDays
    }

    LaunchedEffect(fitnessLevel, mainGoal, objetivo, activityLevel, weight, height) {
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

        // Eliminar ComboBox para nivel de condición física

        // ComboBox para sexo
        Text("Sexo")
        ExposedDropdownMenuBox(
            expanded = expandedSexCombo,
            onExpandedChange = { expandedSexCombo = !expandedSexCombo }
        ) {
            TextField(
                value = if (sex.isEmpty()) "Selecciona tu sexo" else sex,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sexo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSexCombo) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedSexCombo,
                onDismissRequest = { expandedSexCombo = false }
            ) {
                sexes.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            sex = option
                            expandedSexCombo = false
                        },
                        modifier = if (sex == option) Modifier.background(Color(0xFF4CAF50)) else Modifier
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

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
                        modifier = if (objetivo == option) Modifier.background(Color(0xFF4CAF50)) else Modifier
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
                        modifier = if (activityLevel == option) Modifier.background(Color(0xFF4CAF50)) else Modifier
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
        Spacer(modifier = Modifier.height(16.dp))
        Text("Puntaje acumulado: $score", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val userProfile = UserProfile(
                name = name,
                age = age.toIntOrNull() ?: 0,
                sex = sex,
                muscleGroup = "",
                mainGoal = mainGoal,
                motivation = objetivo,
                activityLevel = activityLevel,
                weight = weight.toFloatOrNull() ?: 0f,
                height = height.toFloatOrNull() ?: 0f,
                availableDays = emptyList(), // Ahora siempre vacío
                score = score
            )
            userPreferences.saveUserProfile(userProfile)
            onDataSubmitted(userProfile)
        }) {
            Text("Enviar")
        }
    }
}
