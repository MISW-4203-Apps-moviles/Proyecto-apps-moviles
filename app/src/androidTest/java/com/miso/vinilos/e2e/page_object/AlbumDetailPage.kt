package com.miso.vinilos.e2e.page_object

import android.content.Context
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class AlbumDetailPage(composeRule: ComposeTestRule, activity: MainActivity):
    PageObject(composeRule) {

    val context: Context = activity.applicationContext
    //Navegación y comprobación de la pantalla de detalle de álbumes

    fun validateScreen() {
        waitForElement(hasContentDescription(context.getString(R.string.album_nombre_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_artista_nombre_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_anio_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_disquera_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.album_portada_descripcion)))
    }
}