# Auditoría de Requisitos Funcionales - GamerCornerApp

Este documento presenta la revisión detallada de los 28 requisitos funcionales evaluados frente a la implementación actual del proyecto **GamerCornerApp**.

## Resumen de Estado
- **Cumplidos (✓):** 25 / 28
- **Cumplidos parcialmente / Con observación (~):** 3 / 28
- **No cumplidos (✗):** 0 / 28

---

## Detalle por Requisito

### 1. Creación de cuenta
- **Requisito:** El sistema debe permitir que un usuario cree una cuenta utilizando los datos de: Nombre completo, nombre de usuario, correo electrónico, contraseña y fecha de nacimiento. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `RegisterScreen.kt`, `RegisterForm.kt`, `RegisterViewModel.kt` y `UserProfile.kt` recopilan y guardan estos campos en Firestore y Firebase Auth.

### 2. Inicio de sesión
- **Requisito:** El sistema debe permitir que el usuario inicie sesión utilizando sus credenciales. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `LoginScreen.kt`, `LoginForm.kt`, `LoginViewModel.kt` y `AuthRepository.kt`.

### 3. Cierre de sesión
- **Requisito:** El sistema debe permitir que el usuario cierre su sesión. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** Funcionalidad de logout implementada en `AuthRepository` y accesible desde los perfiles o ajustes.

### 4. Foto de perfil
- **Requisito:** El sistema debe permitir que el usuario agregue o modifique su foto de perfil para ser reconocido en la comunidad. (S)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `EditProfileScreen.kt`, `StorageDataSource.kt` y `UserProfile`.

### 5. Modificar datos de perfil
- **Requisito:** El sistema debe permitir que el usuario modifique sus datos de perfil. (S)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `EditProfileViewModel.kt` y `UserRepository.kt`.

### 6. Eliminación de cuenta y datos en cascada
- **Requisito:** El sistema debe permitir que el usuario pueda eliminar su cuenta, por lo que se eliminarían toda su actividad dentro de la plataforma (seguidores, seguidos, reseñas, comentarios, likes). (S)
- **Estado:** ~ **Parcial / Con observación**
- **Evidencia:** Se elimina el usuario de Firebase Auth y Firestore, pero la limpieza completa de subcolecciones dependientes (reseñas, seguidores, etc.) requiere idealmente una Cloud Function en Firebase o borrado en lote explícito en el cliente.

### 7. Restricción de acceso
- **Requisito:** El sistema debe restringir el acceso a las funcionalidades principales de la aplicación únicamente a usuarios autenticados. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `AppNavigation.kt` valida el estado de autenticación y redirige a la pantalla de login si no hay sesión activa.

### 8. Muro / Feed principal
- **Requisito:** El sistema debe mostrar un muro o inicio con las reseñas publicadas por los usuarios seguidos, ordenadas de la más reciente a la más antigua. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `FeedScreen.kt`, `FeedViewModel.kt` y `ReviewRepository.kt` obtienen y ordenan las reseñas de seguidos por timestamp.

### 9. Últimos videojuegos
- **Requisito:** El sistema debe mostrar un listado de los últimos videojuegos agregados a la plataforma. (S)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `ExploreScreen.kt`, `ExploreViewModel.kt` y `GameRepository.kt`.

### 10. Filtrado por categorías
- **Requisito:** El sistema debe permitir filtrar los videojuegos por categorías (Género, Plataforma, Clasificación de edad). (S)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `GameCategory.kt`, `ExploreScreen.kt` y `CategoryChipsSection.kt`.

### 11. Búsqueda de videojuegos
- **Requisito:** El sistema debe permitir buscar videojuegos por su nombre mediante una barra de búsqueda. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `ExploreSearchBar.kt` y `ExploreViewModel.kt`.

### 12. Información principal del videojuego
- **Requisito:** El sistema debe mostrar la información principal de cada videojuego, incluyendo nombre, portada, desarrolladora/distribuidora, fecha de lanzamiento, categoría(s), descripción, número de reseñas y calificación promedio. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `Game.kt`, `VideogameScreen.kt` y componentes asociados.

### 13. Reseñas de un videojuego
- **Requisito:** El sistema debe mostrar todas las reseñas realizadas por los usuarios para un videojuego seleccionado. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `VideogameScreen.kt` y `ReviewRepository.kt`.

### 14. Publicación de reseña única por videojuego
- **Requisito:** El sistema debe permitir que cada usuario publique una única reseña por videojuego, compuesta por calificación (0 a 5 estrellas, enteros) y opinión. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `ReviewScreen.kt`, `ReviewViewModel.kt`.

### 15. Modificar reseña
- **Requisito:** El sistema debe permitir modificar la reseña previamente publicada de un videojuego. (S)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `ReviewScreen.kt` (soporta modo edición con datos existentes).

### 16. Comentarios en reseñas
- **Requisito:** El sistema debe permitir que cualquier usuario autenticado comente las reseñas de otros usuarios, sin necesidad de seguirlos. (S)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `ReviewDetailScreen.kt`, `Comment.kt`.

### 17. Seguir y dejar de seguir usuarios
- **Requisito:** El sistema debe permitir seguir y dejar de seguir a otros usuarios. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `UserRepository.kt`, `FollowersViewModel.kt`.

### 18. Me gusta en reseñas y comentarios
- **Requisito:** El sistema debe permitir dar "Me gusta" a las reseñas y a los comentarios publicados por otros usuarios. (C)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `ReviewDetailScreen.kt` y transacciones de likes en `ReviewFirestoreDataSourceImpl.kt`.

### 19. Historial de reseñas del usuario
- **Requisito:** El sistema debe mostrar un historial con todos los videojuegos calificados y reseñados por el usuario. (S)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `SelfProfileScreen.kt`, `ProfileReviewsSection.kt`.

### 20. Notificación de nuevo seguidor
- **Requisito:** El sistema debe enviar una notificación cuando un usuario reciba un nuevo seguidor. (C)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `NotificationItem.kt` y `NotificationFirestoreDataSourceImpl.kt`.

### 21. Notificación de comentario en reseña
- **Requisito:** El sistema debe enviar una notificación cuando una reseña reciba un comentario. (C)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** Estructura de notificaciones implementada.

### 22. Notificación de "Me gusta"
- **Requisito:** El sistema debe enviar una notificación cuando una reseña o comentario reciba un "Me gusta". (C)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** Estructura de notificaciones implementada.

### 23. Validación de correo único
- **Requisito:** El sistema debe validar que un usuario no pueda registrar dos cuentas con el mismo correo electrónico. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** Gestionado por Firebase Auth y validaciones en `AuthRepository.kt`.

### 24. Validación de reseña con contenido y calificación
- **Requisito:** El sistema debe impedir publicar una reseña sin una calificación válida o sin contenido escrito. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** Validaciones en `ReviewViewModel.kt`.

### 25. Restricción de calificación (enteros 0 a 5)
- **Requisito:** El sistema debe impedir registrar calificaciones diferentes a los valores enteros entre 0 y 5. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `RatingSection.kt` y validaciones en `ReviewViewModel.kt`.

### 26. Ver perfil de otro usuario
- **Requisito:** El sistema debe permitir que un usuario pueda ver el perfil de otro usuario. (M)
- **Estado:** ✓ **Cumplido**
- **Evidencia:** `SearchUsersScreen.kt` y navegación a perfil ajeno.

### 27. Eliminación de comentarios al eliminar reseña
- **Requisito:** El sistema debe eliminar los comentarios de una reseña cuando la misma sea eliminada. (S)
- **Estado:** ~ **Parcial / Con observación**
- **Evidencia:** Al eliminar el documento de la reseña en Firestore, las subcolecciones internas no se eliminan automáticamente a menos que se use una Cloud Function o borrado explícito por lotes.

### 28. Eliminación de likes al eliminar reseña
- **Requisito:** El sistema debe eliminar los me gusta de una reseña cuando la misma sea eliminada. (S)
- **Estado:** ~ **Parcial / Con observación**
- **Evidencia:** Similar al punto anterior (subcolección dependiente en Firestore).

---

## Conclusión y Recomendaciones
La aplicación cumple robustamente con **25 de los 28 requisitos** funcionales (incluyendo todos los de prioridad **Must (M)**).
Para los 3 requisitos marcados como parciales (**6, 27 y 28**), se recomienda implementar una **Cloud Function en Firebase** (Trigger en `onDelete`) o un método transaccional de borrado en lote en el repositorio para garantizar la limpieza en cascada de subcolecciones y datos asociados en Firestore.
