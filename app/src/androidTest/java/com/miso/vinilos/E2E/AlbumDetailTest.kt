package com.miso.vinilos.E2E

import androidx.activity.compose.setContent
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.miso.vinilos.E2E.page_object.AlbumDetailPage
import com.miso.vinilos.E2E.page_object.AlbumListPage
import com.miso.vinilos.E2E.page_object.UserTypeSelectionPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.ui.theme.VinylsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test




class AlbumDetailTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            VinylsTheme () {
                VinylApp()
            }
        }
    }

    @Test
    fun album_detail_content_success() {

        val itemClickedAlbumName: SemanticsNodeInteraction
        val albumName: String

        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {

            // Spinning loader está presente
            //validateLoader()

            // El contenedor del listado está presente
            validateListElement()

            // Los elementos de la lista están cargados
            validateListItemElement()

            // Obtener el nombre del primer álbum de la lista
            albumName = getAlbumNameFromList(0)

            // Click en el primer elemento de la lista y navegar al detalle
           clickListElement(0)
        }

        with(AlbumDetailPage(composeTestRule, composeTestRule.activity)) {
            // Spinning loader está presente
           validateLoader()

            // La pantalla de detalle del album tiene los elementos necesarios
           validateScreen()

           // El nombre del álbum es el mismo que el del elemento de la lista
           assertText(albumName)
        }
    }
}