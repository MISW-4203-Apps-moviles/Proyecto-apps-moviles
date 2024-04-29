package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class AlbumDetailPage(composeRule: ComposeTestRule, activity: MainActivity):
    PageObject(composeRule) {

    val context = activity.applicationContext
    //Navegación y comprobación de la pantalla de detalle de álbumes
    fun clickAlbumesDetalle() =
        clickTextButton(context.getString(R.string.nav_albumes_label))

    fun verifyAlbumesDetalleLoad() =

        clickListElement()

}