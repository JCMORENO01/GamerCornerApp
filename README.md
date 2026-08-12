# Gamer's Corner

**Gamer's Corner** es una aplicación Android nativa, construida con Jetpack Compose, que funciona como una red social para gamers. Permite a los usuarios crear una cuenta, iniciar sesión, escribir y publicar reseñas de videojuegos, y seguir a otros usuarios de la comunidad. El proyecto implementa la capa de interfaz de una red social orientada a videojuegos. Actualmente cubre el flujo de onboarding, autenticación, publicación de reseñas y gestión de seguidores, todo desarrollado con Jetpack Compose.

## Características

- **Onboarding**: pantalla de bienvenida con presentación de la aplicación.
- **Autenticación**: pantallas de inicio de sesión y creación de cuenta, con opción de continuar con Google.
- **Reseñas de videojuegos**: los usuarios pueden escribir opiniones, calificar juegos y asignar etiquetas (historia, jugabilidad, gráficos, entre otras).
- **Seguidores**: pantalla de seguidores y seguidos con listado de usuarios.
- **Navegación inferior**: acceso a las secciones de Inicio, Explorar, Notificaciones y Perfil.

## Tecnologías

- **Lenguaje**: Kotlin 2.2.10
- **UI Toolkit**: Jetpack Compose (BOM 2026.02.01)
- **Sistema de diseño**: Material 3
- **Build system**: Gradle (Kotlin DSL) con AGP 9.3.1

## Requisitos

- Android Studio (versión reciente, compatible con AGP 9.3.1)
- JDK 11
- SDK de Android:
  - `minSdk`: 26
  - `targetSdk` / `compileSdk`: 37

## Instalación y ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/<usuario>/GamerCornerApp.git
   ```
2. Abrir el proyecto en Android Studio.
3. Esperar a que Gradle sincronice las dependencias.
4. Ejecutar la aplicación en un emulador o dispositivo físico (API 26 o superior) desde el botón Run, o mediante la terminal:
   ```bash
   ./gradlew installDebug
   ```

## Estructura del proyecto

```
app/src/main/java/com/example/gamercornerapp/
├── MainActivity.kt                     # Punto de entrada de la app
└── ui/
    ├── Screens/
    │   ├── startpage/                  # Pantalla de bienvenida (onboarding)
    │   ├── login/                      # Inicio de sesión
    │   ├── register/                   # Creación de cuenta
    │   ├── review/                     # Escribir reseña de un juego
    │   └── followers/                  # Seguidores / seguidos
    │       └── components/             # Componentes específicos (UserRow, etc.)
    ├── componentes/                    # Componentes UI reutilizables (barra inferior, etc.)
    └── theme/                          # Colores, tipografía y tema de la app
```



