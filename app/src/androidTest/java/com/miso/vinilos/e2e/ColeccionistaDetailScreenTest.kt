package com.miso.vinilos.e2e

import androidx.activity.compose.setContent
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.MainActivity
import com.miso.vinilos.e2e.page_object.ArtistaDetailPage
import com.miso.vinilos.e2e.page_object.ArtistaListPage
import com.miso.vinilos.e2e.page_object.ColeccionistaDetailPage
import com.miso.vinilos.e2e.page_object.ColeccionistaListPage
import com.miso.vinilos.e2e.page_object.NavigationSelectionPage
import com.miso.vinilos.e2e.page_object.UserTypeSelectionPage
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.ui.theme.VinylsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ColeccionistaDetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                navController = rememberNavController()
                VinylsTheme {
                    VinylApp(navController)
                }
            }
        }
    }

    @Test
    fun coleccionista_detail_content_success() {

        val coleccionistaName: String

        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickColeccionistas()
            assertCurrentRouteName(navController,"ColeccionistasList")
        }

        with(ColeccionistaListPage(composeTestRule, composeTestRule.activity)) {

            // Spinning loader está presente
            validateLoader()

            // El contenedor del listado está presente
            validateListElement()

            // Los elementos de la lista están presentes
            validateListItemElement()

            // Obtener el nombre del primer coleccionista de la lista
            coleccionistaName = getColeccionistaNameFromList(0)

            // Click en el primer elemento de la lista
            clickListElement(0)
        }

        with(ColeccionistaDetailPage(composeTestRule, composeTestRule.activity)) {

            // La pantalla de detalle del coleccionista tiene los elementos necesarios
            validateScreen()

            // El nombre del coleccionista es el mismo que el del elemento de la lista
            assertText(coleccionistaName)
        }
    }
}