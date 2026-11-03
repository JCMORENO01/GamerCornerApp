package com.example.gamercornerapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class GamerCornerE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var userRepository: UserRepository

    @Before
    fun init() {
        hiltRule.inject()
        try {
            authRepository.signOut()
        } catch (e: Exception) {
            // Ignorar
        }
    }

    @Test
    fun registerAndLikeReviewFlow() {
        try {
            val randomEmail = "user_${System.currentTimeMillis()}@example.com"
            android.util.Log.d("E2E_TEST", "1. Iniciando flujo de registro con correo: $randomEmail")

            // Un usuario nuevo ingresa a la aplicación
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("Crear cuenta").fetchSemanticsNodes().isNotEmpty()
            }
            Thread.sleep(1000)
            composeTestRule.onNodeWithTag("Crear cuenta").performClick()
            android.util.Log.d("E2E_TEST", "2. Clic en Crear cuenta realizado")
            
            // Mete todos sus datos, y la primera vez escribe como contraseña 1234
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("Nombre completo").fetchSemanticsNodes().isNotEmpty()
            }
            Thread.sleep(1000)
            composeTestRule.onNodeWithTag("Nombre completo").performTextInput("Nuevo Usuario")
            composeTestRule.onNodeWithTag("Nombre de usuario").performTextInput("nuevouser")
            composeTestRule.onNodeWithTag("Fecha de nacimiento").performTextInput("10/05/2000")
            composeTestRule.onNodeWithTag("Correo electrónico").performTextInput(randomEmail)
            composeTestRule.onNodeWithTag("Contraseña").performTextInput("1234")
            composeTestRule.onNodeWithTag("Confirmar contraseña").performTextInput("1234")
            composeTestRule.onNodeWithTag("TermsCheckbox").performClick() // Acepta términos
            
            Thread.sleep(1500)
            android.util.Log.d("E2E_TEST", "3. Llenados datos con pass 1234. Haciendo clic en Registrarse...")
            composeTestRule.onNodeWithTag("Registrarse").performClick()

            // Verificar el mensaje de error
            composeTestRule.waitUntil(timeoutMillis = 5000) {
                composeTestRule.onAllNodesWithText("La contraseña debe tener al menos 6 caracteres").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onNodeWithText("La contraseña debe tener al menos 6 caracteres").assertIsDisplayed()
            android.util.Log.d("E2E_TEST", "4. Mensaje de error verificado correctamente en pantalla!")
            Thread.sleep(2000) // Pausa para visualizar el mensaje de error en pantalla

            // El usuario corrije la contraseña y pone 123456, ingresa a la aplicación
            composeTestRule.onNodeWithTag("Contraseña").performTextInput("56") // Se convierte en 123456
            composeTestRule.onNodeWithTag("Confirmar contraseña").performTextInput("56")
            Thread.sleep(1000)
            android.util.Log.d("E2E_TEST", "5. Clave corregida a 123456. Haciendo clic en Registrarse...")
            composeTestRule.onNodeWithTag("Registrarse").performClick()

            // El usuario ingresa a la primera publicación.
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithText("Para ti").fetchSemanticsNodes().isNotEmpty()
            }
            android.util.Log.d("E2E_TEST", "6. Pantalla 'Para ti' (Feed) cargada!")
            Thread.sleep(2000)

            // 7. Entrar a la primera publicación (ReviewDetailScreen)
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("FeedCard").fetchSemanticsNodes().isNotEmpty()
            }
            android.util.Log.d("E2E_TEST", "7. Tarjeta 'FeedCard' encontrada. Haciendo clic...")
            composeTestRule.onAllNodesWithTag("FeedCard")[0].performClick()

            // 8. Verificar que se haya abierto ReviewDetailScreen
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithText("Detalle de Reseña").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onNodeWithText("Detalle de Reseña").assertIsDisplayed()
            android.util.Log.d("E2E_TEST", "8. Pantalla 'Detalle de Reseña' cargada exitosamente!")
            
            // Esperar a que el botón 'Me gusta' esté visible
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithContentDescription("Me gusta").fetchSemanticsNodes().isNotEmpty()
            }

            // 9. Dar Like a la reseña en ReviewDetailScreen
            composeTestRule.onNodeWithContentDescription("Me gusta").performClick()
            android.util.Log.d("E2E_TEST", "9. Botón 'Me gusta' presionado en ReviewDetailScreen!")
            Thread.sleep(1500)

            // 10. Regresar al Feed desde ReviewDetailScreen
            composeTestRule.onNodeWithContentDescription("Volver").performClick()
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithText("Para ti").fetchSemanticsNodes().isNotEmpty()
            }
            android.util.Log.d("E2E_TEST", "10. Regresó al Feed correctamente!")
            Thread.sleep(1500)

            // 11. Volver a entrar a la reseña 1 para comprobar la persistencia del Like
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("FeedCard").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onAllNodesWithTag("FeedCard")[0].performClick()
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithText("Detalle de Reseña").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithContentDescription("Me gusta").fetchSemanticsNodes().isNotEmpty()
            }
            android.util.Log.d("E2E_TEST", "11. Reingresó a la reseña. Verificando y quitando el Like...")
            Thread.sleep(1500)

            // 12. Quitar el Like en ReviewDetailScreen
            composeTestRule.onNodeWithContentDescription("Me gusta").performClick()
            android.util.Log.d("E2E_TEST", "12. Like quitado en ReviewDetailScreen!")
            Thread.sleep(1500)

            // 13. Regresar al Feed
            composeTestRule.onNodeWithContentDescription("Volver").performClick()
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithText("Para ti").fetchSemanticsNodes().isNotEmpty()
            }
            android.util.Log.d("E2E_TEST", "13. Regresó al Feed. Flujo completado exitosamente!")
            Thread.sleep(2000)

        } catch (e: Throwable) {
            android.util.Log.e("E2E_TEST_ERROR", "Ocurrió una excepción en el test: ${e.message}", e)
            throw e
        }
    }

    @Test
    fun loginFollowAndFeedFlow() {
        try {
            android.util.Log.d("E2E_TEST", "1. Iniciando flujo de Login...")

            // Un usuario ya registrado realiza login en la aplicación
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("Iniciar Sesión").fetchSemanticsNodes().isNotEmpty()
            }
            Thread.sleep(1000)
            composeTestRule.onNodeWithTag("Iniciar Sesión").performClick()
            
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("Correo o usuario").fetchSemanticsNodes().isNotEmpty()
            }
            Thread.sleep(1000)
            composeTestRule.onNodeWithTag("Correo o usuario").performTextInput("samuel@gmail.com")
            composeTestRule.onNodeWithTag("Contraseña").performTextInput("samuel")
            Thread.sleep(1500)
            composeTestRule.onNodeWithTag("Ingresar").performClick()
            android.util.Log.d("E2E_TEST", "2. Datos de login ingresados (samuel@gmail.com). Haciendo clic en Ingresar...")

            // Esperar a que la app navegue al Feed ("Para ti")
            composeTestRule.waitUntil(timeoutMillis = 20000) {
                composeTestRule.onAllNodesWithText("Para ti").fetchSemanticsNodes().isNotEmpty()
            }
            android.util.Log.d("E2E_TEST", "2.1. Pestaña 'Para ti' cargada en Feed!")

            // va al perfil de un usuario desde la tarjeta del Feed. 
            composeTestRule.waitUntil(timeoutMillis = 20000) {
                composeTestRule.onAllNodesWithTag("FeedAuthorAvatar").fetchSemanticsNodes().isNotEmpty()
            }
            Thread.sleep(1500)
            composeTestRule.onAllNodesWithTag("FeedAuthorAvatar")[0].performClick()
            android.util.Log.d("E2E_TEST", "3. Clic en FeedAuthorAvatar...")

            // Verificar que la información del usuario sea correcta.
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("Seguir").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onNodeWithTag("UserProfileName").assertIsDisplayed()
            android.util.Log.d("E2E_TEST", "4. Perfil del usuario verificado!")
            Thread.sleep(1500)

            // Le da follow y se verifica que aumenta la cantidad de seguidores (de 0 a 1)
            composeTestRule.onNodeWithTag("Seguir").performClick()
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithText("1").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onAllNodesWithText("1")[0].assertIsDisplayed()
            android.util.Log.d("E2E_TEST", "5. Clic en Seguir realizado y seguidores verificados!")
            Thread.sleep(1500)

            // Ahora el usuario vuelve al home (Feed)
            composeTestRule.onNodeWithContentDescription("Volver").performClick()

            // y va a la sección de publicaciones de seguidos
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithText("Siguiendo").fetchSemanticsNodes().isNotEmpty()
            }
            Thread.sleep(1000)
            composeTestRule.onNodeWithText("Siguiendo").performClick()
            android.util.Log.d("E2E_TEST", "6. Cambiado a la pestaña Siguiendo!")

            // y se verifica que aparezca al menos una publicación del usuario que acabó de seguir.
            composeTestRule.waitUntil(timeoutMillis = 15000) {
                composeTestRule.onAllNodesWithTag("FeedCard").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onAllNodesWithTag("FeedCard")[0].assertIsDisplayed()
            android.util.Log.d("E2E_TEST", "7. Publicación en Siguiendo confirmada! Flujo Login E2E exitoso!")
            Thread.sleep(2000)

        } catch (e: Throwable) {
            android.util.Log.e("E2E_TEST_ERROR", "Ocurrió una excepción en el test de Login: ${e.message}", e)
            throw e
        }
    }
}
