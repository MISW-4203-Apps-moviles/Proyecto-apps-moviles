package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class AlbumDetailPage(composeRule: ComposeTestRule, activity: MainActivity):
    PageObject(composeRule) {

    val context = activity.applicationContext
    //Navegación y comprobación de la pantalla de detalle de álbumes

    fun validateLoader() {
        waitFor(hasContentDescription(context.getString(R.string.cargando_album_descripcion)))
    }
    fun validateScreen() {
        waitForElement(hasContentDescription(context.getString(R.string.album_nombre_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_artista_nombre_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_anio_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_disquera_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_portada_descripcion)))
    }
}