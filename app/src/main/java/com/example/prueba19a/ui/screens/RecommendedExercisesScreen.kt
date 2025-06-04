package com.example.prueba19a.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat

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
            ScrollableTabRow(selectedTabIndex = selectedTabIndex, contentColor = Color.Black) {
                muscleTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, color = if (selectedTabIndex == index) Color.Black else LocalContentColor.current) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Padding entre tabs y contenido
            // Contenido de la rutina según la pestaña seleccionada
            // Mostrar rutina especial para enfermedades
            if (muscleTabs[selectedTabIndex] == "Enfermedades") {
                when (enfermedad) {
                    "Obesidad" -> {
                        val ejercicios = listOf(
                            Ejercicio("Caminata rápida", "obesidad_caminata_rapida", "10-20 min"),
                            Ejercicio("Sentadillas asistidas", "obesidad_sentadillas_asistidas", "10-15 repeticiones"),
                            Ejercicio("Flexiones de pared", "obesidad_flexiones_de_pared", "5-10 repeticiones"),
                            Ejercicio("Elevaciones de talones", "obesidad_elevaciones_de_talones", "10-15 repeticiones"),
                            Ejercicio("Marcha en el lugar con brazos", "obesidad_marcha_en_el_lugar_con_el_brazo", "5-10 min")
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 420.dp)
                                .align(Alignment.CenterHorizontally),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ejercicios.forEach { ejercicio ->
                                    val drawableId = getDrawableIdByName(ejercicio.nombreImagen)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 8.dp)) {
                                        Text(text = "${ejercicio.nombre}  |  ${ejercicio.repeticiones}", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        if (drawableId != 0) {
                                            Image(
                                                painter = painterResource(id = drawableId),
                                                contentDescription = ejercicio.nombre,
                                                modifier = Modifier.size(192.dp)
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.size(192.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Aquí puedes agregar más enfermedades y rutinas si lo deseas
                }
            } else if (muscleTabs[selectedTabIndex] == "Lesiones") {
                when (lesion) {
                    "Esguince" -> {
                        val ejercicios = listOf(
                            Ejercicio("Movilidad articular", "esguince_movilidad_articular", "1-2 min"),
                            Ejercicio("Flexión y extensión de tobillo", "esguince_flexion_y_extension_de_tobillo", "1-2 min"),
                            Ejercicio("Fortalecimiento isométrico", "esguince_fortalecimiento_isometrico", "5-10 repeticiones"),
                            Ejercicio("Elevación de talones", "esguince_elevacion_de_talones", "10-15 repeticiones"),
                            Ejercicio("Ejercicio de equilibrio", "esguince_ejercicio_de_equilibrio", "10-30 seg")
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 420.dp)
                                .align(Alignment.CenterHorizontally),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ejercicios.forEach { ejercicio ->
                                    val drawableId = getDrawableIdByName(ejercicio.nombreImagen)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 8.dp)) {
                                        Text(text = "${ejercicio.nombre}  |  ${ejercicio.repeticiones}", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        if (drawableId != 0) {
                                            Image(
                                                painter = painterResource(id = drawableId),
                                                contentDescription = ejercicio.nombre,
                                                modifier = Modifier.size(192.dp)
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.size(192.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Aquí puedes agregar más lesiones y rutinas si lo deseas
                }
            } else if (muscleTabs[selectedTabIndex] == "Dificultad Médica") {
                when (dificultadMedica) {
                    "Problemas de espalda" -> {
                        val ejercicios = listOf(
                            Ejercicio("Estiramiento gato-vaca", "problemas_de_espalda_estiramiento_gato_vaca", "10-15 repeticiones"),
                            Ejercicio("Plancha", "problemas_de_espalda_plancha", "20-30 seg"),
                            Ejercicio("Estiramiento de piriforme", "problemas_de_espalda_estiramiento_piriforme", "20-30 seg por lado"),
                            Ejercicio("Rotación de tronco", "problemas_de_espalda_rotacion_de_tronco", "5-10 por lado"),
                            Ejercicio("Puente", "problemas_de_espalda_puente", "10-15 repeticiones")
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 420.dp)
                                .align(Alignment.CenterHorizontally),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ejercicios.forEach { ejercicio ->
                                    val drawableId = getDrawableIdByName(ejercicio.nombreImagen)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 8.dp)) {
                                        Text(text = "${ejercicio.nombre}  |  ${ejercicio.repeticiones}", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        if (drawableId != 0) {
                                            Image(
                                                painter = painterResource(id = drawableId),
                                                contentDescription = ejercicio.nombre,
                                                modifier = Modifier.size(192.dp)
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.size(192.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Aquí puedes agregar más dificultades médicas y rutinas si lo deseas
                }
            } else {
                // Mostrar rutina de abdominales y demás grupos según el puntaje (score) calculado en UserDataScreen
                val ejercicios = getRoutineList(muscleTabs[selectedTabIndex], score = score.toString())
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 420.dp)
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Mostrar nombre y repeticiones arriba, imagen abajo
                        ejercicios.forEach { ejercicio ->
                            val drawableId = getDrawableIdByName(ejercicio.nombreImagen)
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 8.dp)) {
                                Text(text = "${ejercicio.nombre}  |  ${ejercicio.repeticiones}", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(8.dp))
                                if (drawableId != 0) {
                                    Image(
                                        painter = painterResource(id = drawableId),
                                        contentDescription = ejercicio.nombre,
                                        modifier = Modifier.size(192.dp) // Tamaño 3 veces más grande
                                    )
                                } else {
                                    Spacer(modifier = Modifier.size(192.dp)) // Espacio vacío si no hay imagen
                                }
                            }
                        }
                        if (ejercicios.isEmpty()) {
                            Text("No hay ejercicios para este grupo.", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
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
                    Button(onClick = { showUserProfile = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black), modifier = Modifier.fillMaxWidth()) {
                        Text("Cerrar", color = Color.White)
                    }
                }
            }
        }
    }
}

// 1. Data class para ejercicio
data class Ejercicio(val nombre: String, val nombreImagen: String, val repeticiones: String)

// 2. Nueva función para obtener la lista de ejercicios y su imagen
fun getRoutineList(muscleGroup: String, score: String): List<Ejercicio> {
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
            "avanzado" -> listOf(
                Ejercicio("Sentadilla búlgara (en un banco)", "pierna_avanzado_sentadilla_bulgara", "4x12"),
                Ejercicio("Pistols (una pierna)", "pierna_avanzado_pistol_squats", "3x8 por pierna"),
                Ejercicio("Zancadas cortas (con peso)", "pierna_avanzado_zancadas_con_peso", "4x10 por pierna"),
                Ejercicio("Sentadilla con saltos", "pierna_avanzado_sentadilla_explosiva", "3x15"),
                Ejercicio("Puente de glúteos", "pierna_avanzado_punte_de_gluteos", "4x15")
            )
            "intermedio" -> listOf(
                Ejercicio("Sentadilla (con peso)", "pierna_intermedio_sentadilla_con_peso", "4x12"),
                Ejercicio("Sentadillas búlgaras (en banco)", "pierna_intermedio_sentadilla_bulgara", "3x10 por pierna"),
                Ejercicio("Peso muerto romano (con peso)", "pierna_intermedio_peso_muerto_rumano", "4x10"),
                Ejercicio("Sentadilla sumo", "pierna_intermedio_sentadilla_sumo", "3x12"),
                Ejercicio("Elevación de talones a una pierna", "pierna_intermedio_elevacion_de_talones", "3x12 por pierna")
            )
            else -> listOf(
                Ejercicio("Sentadillas", "pierna_principiante_sentadilla", "3x15"),
                Ejercicio("Zancadas", "pierna_principiante_zancadas_estaticass", "3x10 por pierna"),
                Ejercicio("Puente de glúteos", "pierna_principiante_punte_de_gluteos", "3x12"),
                Ejercicio("Saltos bipodales", "pierna_principiante_saltos_bi_podales", "3x15"),
                Ejercicio("Elevación de talones", "pierna_principiante_elevacion_de_talones", "3x15")
            )
        }
        "Pecho" -> when (nivel) {
            "avanzado" -> listOf(
                Ejercicio("Flexiones explosivas", "pecho_avanzado_flexiones_explosivas", "4x12"),
                Ejercicio("Flexiones estándar", "pecho_avanzado_flexiones_basicas", "4x15"),
                Ejercicio("Flexiones con pies elevados", "pecho_avanzado_flexiones_inclinadas_con_pies_elevados", "4x10"),
                Ejercicio("Flexiones a una mano", "pecho_avanzado_flexiones_a_una_mano", "3x6 por lado"),
                Ejercicio("Fondos en silla con peso", "pecho_avanzado_fondos_en_silla", "4x10")
            )
            "intermedio" -> listOf(
                Ejercicio("Flexiones estándar", "pecho_intermedio_flexiones_basicas", "4x12"),
                Ejercicio("Flexiones declinadas", "pecho_intermedio_flexiones_declinadas", "3x10"),
                Ejercicio("Flexiones en diamante", "pecho_intermedio_flexiones_diamante", "3x10"),
                Ejercicio("Fondos en silla", "pecho_intermedio_fondos_en_silla", "3x12"),
                Ejercicio("Flexiones abiertas", "pecho_intermedio_flexiones_abiertas", "3x12")
            )
            else -> listOf(
                Ejercicio("Flexiones con rodillas", "pecho_principiante_flexiones_con_rodillas", "3x10"),
                Ejercicio("Flexiones inclinadas", "pecho_principiante_flexiones_inclinadas", "3x10"),
                Ejercicio("Flexiones declinadas", "pecho_principiante_flexiones_declinadas", "3x8"),
                Ejercicio("Apertura con liga de estiramiento", "pecho_principiante_apertura_con_liga_de_estiramiento", "3x12")
            )
        }
        "Abdominales" -> when (nivel) {
            "avanzado" -> listOf(
                Ejercicio("Crunch en V", "abdomen_avanzado_cruch_en_v", "4x15"),
                Ejercicio("Elevaciones de piernas", "abdomen_avanzado_elevaciones_de_piernas", "4x15"),
                Ejercicio("Dragón flags", "abdomen_avanzado_dragon_flags", "3x8"),
                Ejercicio("Plancha en caminata", "abdomen_avanzado_plancha_en_caminata", "3x40 seg")
            )
            "intermedio" -> listOf(
                Ejercicio("Crunch bicicleta", "abdomen_intermedio_cruch_en_bicicleta", "3x20"),
                Ejercicio("Crunch de tijera", "abdomen_intermedio_cruch_en_tijera", "3x15"),
                Ejercicio("Elevaciones de piernas", "abdomen_intermedio_elevaciones_de_piernas", "3x15"),
                Ejercicio("Plancha lateral", "abdomen_intermedio_plancha_lateral", "3x30 seg por lado")
            )
            else -> listOf(
                Ejercicio("Crunch básico", "abdomen_principiantes_cruch_basico", "3x15"),
                Ejercicio("Elevaciones de pierna", "abdomen_principiantes_elevaciones_de_piernas", "3x12"),
                Ejercicio("Plancha", "abdomen_principiantes_plancha", "3x30 seg"),
                Ejercicio("Crunch invertido", "abdomen_principiantes_cruch_invertido", "3x12")
            )
        }
        "Brazos" -> when (nivel) {
            "avanzado" -> listOf(
                Ejercicio("Curl de bíceps inclinado", "brazo_avanzado_curl_de_biceps_inclinado", "4x10"),
                Ejercicio("Remos australianos unilateral", "brazo_avanzado_remos_autralianas", "4x8 por lado"),
                Ejercicio("Flexiones en puño", "brazo_avanzado_flexiones_en_pino", "3x8"),
                Ejercicio("Flexiones explosivas", "brazo_avanzado_flexiones_explosivas", "4x10"),
                Ejercicio("Fondos en silla con peso", "brazo_avanzado_fondos_en_silla", "4x10")
            )
            "intermedio" -> listOf(
                Ejercicio("Curl de bícep (con mancuerna)", "brazo_intermedio_curl_de_biceps", "4x12"),
                Ejercicio("Remos australianos (en barra)", "brazo_intermedio_remos_australianos", "4x10"),
                Ejercicio("Curl de bíceps concentrado (mancuerna)", "brazo_intermedio_curl_de_bicep_concetrado", "3x10"),
                Ejercicio("Flexiones en diamante", "brazo_intermedio_flexiones_diamante", "3x12"),
                Ejercicio("Fondos en silla con piernas elevadas", "brazo_intermedio_fondos_inclinados", "3x12")
            )
            else -> listOf(
                Ejercicio("Curl de bícep (botella/manc. pequeña)", "brazo_principiante_curl_de_biceps", "3x12"),
                Ejercicio("Flexiones invertidas", "brazo_principiante_flexiones_invertidas", "3x10"),
                Ejercicio("Fondos en silla", "brazo_principiante_fondos_en_silla", "3x10"),
                Ejercicio("Extensión de tríceps (botella/manc.)", "brazo_principiante_extencion_de_triceps_con_peso", "3x12"),
                Ejercicio("Flexiones en diamante", "brazo_principiante_flexiones_diamante", "3x10")
            )
        }
        "Cardio" -> when (nivel) {
            "avanzado" -> listOf(
                Ejercicio("Rodillas a pecho explosiva", "cardio_avanzado_salto_con_rodillas_al_pecho", "4x20"),
                Ejercicio("Burpees (sin flexión)", "cardio_avanzado_burpees", "4x15"),
                Ejercicio("Plancha con toque de hombros", "cardio_avanzado_plancha_con_toques_de_hombros", "3x30 seg"),
                Ejercicio("Salto de cuerda", "cardio_avanzado_salto_de_cuerda", "4x1 min"),
                Ejercicio("Sentadillas explosivas", "cardio_avanzado_sentadilla_explosiva", "4x15")
            )
            "intermedio" -> listOf(
                Ejercicio("Marcha rápida", "cardio_intermedio_marcha_rapida", "4x2 min"),
                Ejercicio("Saltos de tijera", "cardio_intermedio_saltos_de_tijera", "4x20"),
                Ejercicio("Rodillas al pecho", "cardio_intermedio_salto_con_rodillas_al_pecho", "4x15"),
                Ejercicio("Talones al glúteo", "cardio_intermedio_punte_de_gluteos", "4x20"),
                Ejercicio("Saltar la cuerda", "cardio_intermedio_salto_de_cuerda", "4x1 min")
            )
            else -> listOf(
                Ejercicio("Marcha estática", "cardio_principiante_caminata_estatica", "3x2 min"),
                Ejercicio("Saltos de tijera", "cardio_principiante_saltos_de_tijera", "3x15"),
                Ejercicio("Saltos bipodales", "cardio_principiante_saltos_bi_podales", "3x12"),
                Ejercicio("Trote suave", "cardio_principiante_trotar", "3x2 min"),
                Ejercicio("Boxeo de sombra", "cardio_principiante_boxeo_de_sombra", "3x1 min")
            )
        }
        else -> emptyList()
    }
}

// Función utilitaria para obtener el id del recurso drawable por nombre
@Composable
fun getDrawableIdByName(nombre: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(nombre, "drawable", context.packageName)
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
