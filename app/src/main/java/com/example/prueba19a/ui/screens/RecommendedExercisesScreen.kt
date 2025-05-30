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
    enfermedad: String = "", // Valor por defecto para evitar error si no se pasa
    onBack: () -> Unit
) {
    // Estado para controlar la visibilidad de los datos del usuario
    var showUserProfile by remember { mutableStateOf(false) }

    // Tabs para grupos musculares
    val baseMuscleTabs = listOf("Pierna", "Brazos", "Cardio", "Pecho", "Abdominales")
    val showLesionesTab = lesion == "Esguince" || lesion == "Recuperación de fracturas" || lesion == "Luxaciones" || lesion == "Lesiones de meniscos"
    val showEnfermedadesTab = enfermedad.isNotEmpty() && enfermedad != "No"
    val muscleTabs = buildList {
        addAll(baseMuscleTabs)
        if (showLesionesTab) add("Lesiones")
        if (showEnfermedadesTab) add("Enfermedades")
    }
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
        // Mostrar rutina especial para enfermedades
        if (muscleTabs[selectedTabIndex] == "Enfermedades") {
            when (enfermedad) {
                "Obesidad" -> Text(
                    """
Rutina para Obesidad

- Caminar a un ritmo acelerado durante 10-20 minutos.
- Sentadillas asistidas
  Duración: 10-15 repeticiones
- Flexiones de Pared
  Repeticiones: 5-10 repeticiones
- Elevaciones de talones
  Repeticiones: 10-15 repeticiones
- Marcha en el lugar con brazos
  Hecho en el lugar mientras mueves los brazos hacia arriba y hacia abajo.
  5-10 minutos
""".trimIndent(),
                    style = MaterialTheme.typography.bodyLarge
                )
                // Aquí puedes agregar más enfermedades y rutinas si lo deseas
            }
        } else if (muscleTabs[selectedTabIndex] == "Lesiones") {
            when (lesion) {
                "Esguince" -> {
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
                }
                "Recuperación de fracturas" -> {
                    Text(
                        """
Ejercicios para la Recuperación de Fracturas

1. Movilidad Articular Suave
Descripción: Mueve suavemente la articulación cercana a la fractura.
Ejercicio:
Si la fractura es en el brazo, mueve suavemente los dedos y la muñeca.
Si es en la pierna, mueve los dedos del pie y el tobillo.

2. Estiramientos Isométricos
Descripción: Fortalece los músculos sin mover la articulación afectada.
Ejercicio:
Presiona suavemente el brazo o la pierna contra una superficie (como una pared) sin moverlo. Mantén la presión durante 5-10 segundos y relaja.

3. Elevación de Miembros
Descripción: Reduce la hinchazón y mejora la circulación.
Ejercicio:
Si tienes una fractura en la pierna, eleva la pierna sobre una almohada mientras estás acostado o sentado.

4. Ejercicios de Respiración Profunda
Descripción: Mejora la circulación y ayuda a la recuperación general.
Ejercicio:
Siéntate o acuéstate cómodamente y respira profundamente, llenando tus pulmones de aire. Mantén la respiración durante unos segundos y exhala lentamente.

5. Caminar (si está permitido)
Descripción: Mejora la movilidad y la circulación.
Ejercicio:
Si tu médico lo permite, comienza a caminar con apoyo (muletas o un andador) para mantener la movilidad.

6. Flexiones de Tobillo (si es en la pierna)
Descripción: Mejora la movilidad del tobillo.
Ejercicio:
Siéntate con la pierna estirada y mueve el tobillo hacia arriba y hacia abajo suavemente.
""".trimIndent(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                "Luxaciones" -> {
                    Text(
                        """
Ejercicios para la Recuperación de Luxaciones

1. Movilidad Suave
Descripción: Mejora la movilidad de la articulación afectada sin forzar.
Ejercicio:
Para el hombro: Siéntate o párate y deja que el brazo afectado cuelgue. Mueve suavemente el brazo en círculos pequeños en ambas direcciones.
Para el tobillo: Siéntate y mueve el tobillo hacia arriba y hacia abajo, y realiza movimientos circulares.

2. Estiramientos Suaves
Descripción: Alivia la tensión en los músculos alrededor de la articulación.
Ejercicio:
Hombro: Lleva el brazo afectado hacia el lado opuesto del cuerpo, usando la otra mano para ayudar suavemente. Mantén la posición durante 15-30 segundos.
Tobillo: Estira el pie hacia arriba y hacia abajo mientras estás sentado.

3. Fortalecimiento Isométrico
Descripción: Fortalece los músculos sin mover la articulación.
Ejercicio:
Hombro: Presiona la palma de la mano contra una pared o superficie sólida sin mover el brazo. Mantén durante 5-10 segundos.
Tobillo: Presiona el pie contra una superficie suave (como una almohada) sin moverlo. Mantén durante 5-10 segundos.

4. Ejercicios de Equilibrio
Descripción: Mejora la estabilidad general.
Ejercicio:
Tobillo: Párate sobre una pierna (la no afectada) y mantén el equilibrio durante 10-30 segundos. Si te sientes seguro, intenta hacerlo con los ojos cerrados.

5. Movimientos de Flexión y Extensión
Descripción: Mejora la movilidad y la fuerza.
Ejercicio:
Hombro: Con el brazo afectado colgando, levanta el brazo hacia adelante (flexión) y hacia los lados (abducción) sin forzar el movimiento.
Tobillo: Flexiona y extiende el pie lentamente mientras estás sentado.
""".trimIndent(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                "Lesiones de meniscos" -> {
                    Text(
                        """
Ejercicios para Principiantes con Lesión de Meniscos

1. Movimientos de Tobillo
Descripción: Mejora la movilidad y circulación.
Ejercicio:
Siéntate en una silla y mueve el tobillo hacia arriba y hacia abajo, y realiza movimientos circulares. Haz 10 repeticiones en cada dirección.

2. Estiramiento de Cuádriceps
Descripción: Alivia la tensión en la parte frontal del muslo.
Ejercicio:
De pie, sujétate de una pared o una silla. Doble la rodilla afectada y lleva el talón hacia los glúteos. Mantén la posición durante 15-20 segundos y repite 2-3 veces.

3. Flexiones de Rodilla (con apoyo)
Descripción: Mejora la fuerza y movilidad de la rodilla.
Ejercicio:
De pie, con una silla o mesa para apoyo, flexiona la rodilla suavemente hacia atrás, levantando el pie del suelo. Mantén durante 5 segundos y baja. Repite 10-15 veces.

4. Elevación de Talones
Descripción: Fortalece los músculos de la pantorrilla.
Ejercicio:
Párate con los pies a la altura de los hombros. Levanta lentamente los talones del suelo y mantén la posición durante unos segundos antes de bajar. Repite 10-15 veces.

5. Deslizamiento de Talón
Descripción: Mejora la movilidad de la rodilla.
Ejercicio:
Acostado boca arriba, desliza el talón de la pierna afectada hacia los glúteos mientras mantienes el pie en el suelo. Luego, desliza el pie de regreso a la posición inicial. Repite 10-15 veces.
""".trimIndent(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
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
