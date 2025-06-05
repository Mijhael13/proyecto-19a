# Proyecto 19A - App de Rutinas de Ejercicio Personalizadas

Esta aplicación móvil está diseñada para recomendar rutinas de ejercicio personalizadas según el perfil, objetivos y condiciones de salud del usuario. Utiliza Jetpack Compose para la interfaz y permite una experiencia visual moderna y adaptable.

## Características principales

- **Rutinas personalizadas**: El usuario recibe recomendaciones de ejercicios según su edad, peso, altura, nivel de actividad, objetivo físico, lesiones, enfermedades o dificultades médicas.
- **Visualización de ejercicios**: Cada ejercicio muestra su nombre, repeticiones sugeridas y una imagen ilustrativa.
- **Soporte para condiciones especiales**: Incluye rutinas adaptadas para personas con obesidad, problemas de espalda, esguinces, fracturas, luxaciones y lesiones de meniscos.
- **Selección de grupo muscular**: El usuario puede navegar entre rutinas para pierna, brazos, cardio, pecho y abdominales.
- **Perfil de usuario**: Permite guardar y editar los datos personales y preferencias del usuario.
- **Interfaz moderna**: Uso de colores neutros y navegación intuitiva.

## Estructura del proyecto

- `app/src/main/java/com/example/prueba19a/ui/screens/` - Pantallas principales de la app (UserDataScreen, RecommendedExercisesScreen, etc).
- `app/src/main/res/drawable/` - Imágenes de ejercicios y recursos visuales.
- `app/src/main/res/mipmap-*/` - Iconos de la aplicación para diferentes resoluciones.
- `app/src/main/AndroidManifest.xml` - Configuración principal de la app.

## Personalización de rutinas

La app calcula un puntaje basado en los datos del usuario y selecciona la dificultad y tipo de ejercicios más adecuados. Si el usuario indica alguna lesión, enfermedad o dificultad médica, la rutina se adapta automáticamente para priorizar la seguridad y la recuperación.

## Cómo cambiar el ícono de la app

1. Coloca tu imagen en `app/src/main/res/drawable/` (por ejemplo, `icono.jpeg`).
2. Usa un generador de íconos de Android para crear los archivos `ic_launcher.png` en las carpetas `mipmap-*`.
3. Reemplaza los archivos existentes en las carpetas `mipmap-*`.
4. No cambies la referencia en el `AndroidManifest.xml` (debe ser `@mipmap/ic_launcher`).

## Requisitos

- Android Studio Bumblebee o superior
- Gradle 7+
- SDK mínimo: 21 (Android 5.0)

## Instalación y ejecución

1. Clona el repositorio o descarga el código fuente.
2. Abre el proyecto en Android Studio.
3. Sincroniza el proyecto con Gradle.
4. Ejecuta la app en un emulador o dispositivo físico.

## Créditos

Desarrollado por Mijhael Valenzuela y colaboradores. Imágenes de ejercicios propias o libres de derechos.

---
