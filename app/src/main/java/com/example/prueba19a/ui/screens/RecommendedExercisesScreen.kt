package com.example.prueba19a.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.background
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
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
    lesion: String = "",
    enfermedad: String = "",
    dificultadMedica: String = "",
    score: Int = 0, // <-- Agregar score como parámetro obligatorio
    onBack: () -> Unit
) {
    var showUserProfile by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val baseMuscleTabs = listOf("Pierna", "Brazos", "Cardio", "Pecho", "Abdominales")
    val showLesionesTab = lesion == "Esguince" || lesion == "Recuperación de fracturas" || lesion == "Luxaciones" || lesion == "Lesiones de meniscos"
    val showEnfermedadesTab = enfermedad.isNotEmpty() && enfermedad != "No"
    val showDificultadMedicaTab = dificultadMedica.isNotEmpty() && dificultadMedica != "No"
    // Construir el orden de las pestañas: primero lesiones, luego enfermedades, luego dificultades médicas, luego los grupos musculares base
    val muscleTabs = buildList {
        if (showLesionesTab) add("Lesiones")
        if (showEnfermedadesTab) add("Enfermedades")
        if (showDificultadMedicaTab) add("Dificultad Médica")
        addAll(baseMuscleTabs)
    }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 32.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón de perfil
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { showUserProfile = true }) {
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
            } else if (muscleTabs[selectedTabIndex] == "Dificultad Médica") {
                when (dificultadMedica) {
                    "Tendinitis" -> Text(
                        """
Rutina para Tendinitis

1. Estiramiento de muñeca
Descripción: Estira suavemente la muñeca afectada hacia abajo y hacia arriba, manteniendo cada posición durante 15-20 segundos. Repite 2-3 veces.

2. Fortalecimiento isométrico
Descripción: Presiona la palma de la mano contra una superficie (como una mesa) sin mover la muñeca. Mantén la presión durante 5-10 segundos y relaja. Repite 5 veces.

3. Movilidad suave
Descripción: Realiza movimientos circulares lentos con la muñeca y el antebrazo. Haz 10 repeticiones en cada dirección.

4. Compresas frías
Descripción: Aplica una compresa fría sobre la zona afectada durante 10-15 minutos después de los ejercicios.

5. Descanso activo
Descripción: Evita actividades que causen dolor, pero mantén la movilidad suave de la articulación.
""".trimIndent(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    "Problemas de espalda" -> Text(
                        """
Rutina para Problemas de Espalda

- Estiramiento en 4 patas (manos y rodillas):
  Arquear la espalda hacia arriba (posición de gato) y luego hacia abajo (posición de vaca).
  Repeticiones: 10-15 veces

- Plancha:
  Duración: 20-30 segundos, aumentando gradualmente.

- Estiramiento de piriforme:
  Duración: 20-30 segundos por lado

- Rotación de tronco:
  Repeticiones: 5-10 por lado

- Puente:
  Repeticiones: 10-15 veces
""".trimIndent(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    // Aquí puedes agregar más dificultades médicas y rutinas si lo deseas
                }
            } else {
                // Mostrar rutina de abdominales y demás grupos según el puntaje (score) calculado en UserDataScreen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 420.dp)
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        getRoutine(muscleTabs[selectedTabIndex], score = score.toString()),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A98B8)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }

        if (showUserProfile) {
            ModalBottomSheet(
                onDismissRequest = { showUserProfile = false },
                sheetState = sheetState
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Perfil del Usuario", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 16.dp))
                    Text("Nombre: $userName", modifier = Modifier.padding(vertical = 4.dp))
                    Text("Edad: $userAge", modifier = Modifier.padding(vertical = 4.dp))
                    Text("Objetivo: ${userMotivation.ifBlank { "No especificado" }}", modifier = Modifier.padding(vertical = 4.dp))
                    Text("Nivel de actividad: $userActivityLevel", modifier = Modifier.padding(vertical = 4.dp))
                    Text("Peso: $userWeight kg", modifier = Modifier.padding(vertical = 4.dp))
                    Text("Altura: $userHeight cm", modifier = Modifier.padding(vertical = 4.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showUserProfile = false }, modifier = Modifier.fillMaxWidth()) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}

// Función para obtener la rutina según el grupo muscular y el puntaje
fun getRoutine(muscleGroup: String, score: String): String {
    // Determinar el nivel según el puntaje numérico
    val nivel = score.toIntOrNull()?.let {
        when {
            it <= 5 -> "principiante"
            it in 6..10 -> "intermedio"
            it >= 11 -> "avanzado"
            else -> "principiante"
        }
    } ?: score.trim().lowercase()
    return when (muscleGroup) {
        "Pierna" -> when (nivel) {
            "avanzado" -> """
Avanzado · Pierna

1. Sentadilla búlgara (en un banco)
   4x12-15
2. Pistols (una pierna)
   3x6-8 c/pierna
3. Zancadas cortas (con peso)
   3x15-20
4. Sentadilla con saltos
   3x10-12
5. Puente de glúteos
   3x12-15
""".trimIndent()
            "intermedio" -> """
Intermedio · Pierna

1. Sentadilla (con peso)
   3-4x12-15
2. Sentadillas búlgaras (en banco)
   4x10-12 c/pierna
3. Peso muerto romano (con peso)
   3x10-12
4. Sentadilla sumo
   4x12-15
5. Elevación de talones a una pierna
   3x12-15
""".trimIndent()
            else -> """
Principiante · Pierna

1. Sentadillas
   3x10-12
2. Zancadas
   3x8-10 c/pierna
3. Puente de glúteos
   3x12-15
4. Saltos bipodales
   3x15-20
5. Elevación de talones
   3x15-20
""".trimIndent()
        }
        "Pecho" -> when (nivel) {
            "avanzado" -> """
Avanzado · Pecho

1. Flexiones explosivas
   4x8-12
2. Flexiones estándar
   4x15-18
3. Flexiones con pies elevados
   4x12-15
4. Flexiones a una mano
   3x5-8 c/mano
5. Fondos en silla con peso
   3x12-15
""".trimIndent()
            "intermedio" -> """
Intermedio · Pecho

1. Flexiones estándar
   4x10-15
2. Flexiones declinadas
   4x10-12
3. Flexiones en diamante
   4x10-12
4. Fondos en silla
   3x12-15
5. Flexiones abiertas
   3x8-10
""".trimIndent()
            else -> """
Principiante · Pecho

1. Flexiones con rodillas
   4x10-12
2. Flexiones inclinadas
   4x10-12
3. Flexiones declinadas
   4x10-8
4. Apertura con liga de estiramiento
   3x12-15
""".trimIndent()
        }
        "Abdominales" -> when (nivel) {
            "avanzado" -> """
Avanzado · Abdomen

1. Crunch en V
   4x12-15
2. Elevaciones de piernas
   4x15-18
3. Dragón flags
   3x30 segs
4. Plancha en caminata
   3x45 segs
""".trimIndent()
            "intermedio" -> """
Intermedio · Abdomen

1. Crunch bicicleta
   4x12-15
2. Crunch de tijera
   4x12-15
3. Elevaciones de piernas
   4x12-15
4. Plancha lateral
   4x30 segs c/lado
""".trimIndent()
            else -> """
Principiante · Abdomen

1. Crunch básico
   4x10-15
2. Elevaciones de pierna
   4x10-15
3. Plancha
   4x40 segs
4. Crunch invertido
   4x10-15
""".trimIndent()
        }
        "Brazos" -> when (nivel) {
            "avanzado" -> """
Avanzado · Brazos

1. Curl de bíceps inclinado
   4x8-10
2. Remos australianos unilateral
   3x6-8
3. Flexiones en puño
   4x3-5
4. Flexiones explosivas
   4x6-10
5. Fondos en silla con peso
   3x12-15
""".trimIndent()
            "intermedio" -> """
Intermedio · Brazos

1. Curl de bícep (con mancuerna)
   4x12-15
2. Remos australianos (en barra)
   4x10-12
3. Curl de bíceps concentrado (mancuerna)
   3x10-12
4. Flexiones en diamante
   4x12-15
5. Fondos en silla con piernas elevadas
   4x12-15
""".trimIndent()
            else -> """
Principiante · Brazos

1. Curl de bícep (botella/manc. pequeña)
   3x10-12
2. Flexiones invertidas
   3x8-12
3. Fondos en silla
   3x10-16
4. Extensión de tríceps (botella/manc.)
   3x12-15
5. Flexiones en diamante
   3x10-12
""".trimIndent()
        }
        "Cardio" -> when (nivel) {
            "avanzado" -> """
Avanzado · Cardio

1. Rodillas a pecho explosiva
   4x60 segs
2. Burpees (sin flexión)
   4x45 segs
3. Plancha con toque de hombros
   4x60 segs
4. Salto de cuerda
   4x45 segs
5. Sentadillas explosivas
   3x10-15
""".trimIndent()
            "intermedio" -> """
Intermedio · Cardio

1. Marcha rápida
   4x45 segs
2. Saltos de tijera
   4x45 segs
3. Rodillas al pecho
   4x45 segs
4. Talones al glúteo
   4x45 segs
5. Saltar la cuerda
   3x30 segs
""".trimIndent()
            else -> """
Principiante · Cardio

1. Marcha estática
   4x45 segs
2. Saltos de tijera
   4x30 segs
3. Saltos bipodales
   4x30 segs
4. Trote suave
   3x1:30 min
5. Boxeo de sombra
   3x30 segs
""".trimIndent()
        }
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
