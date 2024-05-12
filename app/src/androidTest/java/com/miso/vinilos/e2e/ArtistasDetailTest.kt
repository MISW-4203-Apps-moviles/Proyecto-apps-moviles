package com.miso.vinilos.E2E

import androidx.activity.compose.setContent
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.E2E.page_object.AlbumDetailPage
import com.miso.vinilos.E2E.page_object.AlbumListPage
import com.miso.vinilos.E2E.page_object.ArtistaDetailPage
import com.miso.vinilos.E2E.page_object.ArtistaListPage
import com.miso.vinilos.E2E.page_object.NavigationSelectionPage
import com.miso.vinilos.E2E.page_object.UserTypeSelectionPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.ui.theme.VinylsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
class ArtistasDetailTest {

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
    fun artista_detail_content_success() {

        val itemClickedAlbumName: SemanticsNodeInteraction
        val artistaName: String

        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickArtistas()
            assertCurrentRouteName(navController,"ArtistasList")
        }

        with(ArtistaListPage(composeTestRule, composeTestRule.activity)) {

            // Spinning loader está presente
            validateLoader()

            // El contenedor del listado está presente
            validateListElement()

            // Los elementos de la lista están presentes
            validateListItemElement()

            // Obtener el nombre del primer artista de la lista
            artistaName = getArtistaNameFromList(0)

            // Click en el primer elemento de la lista
            clickListElement(0)
        }

        with(ArtistaDetailPage(composeTestRule, composeTestRule.activity)) {

            // La pantalla de detalle del album tiene los elementos necesarios
            validateScreen()

            // El nombre del álbum es el mismo que el del elemento de la lista
            assertText(artistaName)
        }
    }
}