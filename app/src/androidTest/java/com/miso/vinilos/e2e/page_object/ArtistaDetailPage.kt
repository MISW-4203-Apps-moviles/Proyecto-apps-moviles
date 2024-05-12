package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class ArtistaDetailPage(composeRule: ComposeTestRule, activity: MainActivity):
    PageObject(composeRule) {

    val context = activity.applicationContext

    fun validateScreen() {
        waitForElement(hasContentDescription(context.getString(R.string.artista_nombre)))
        waitForElement(hasContentDescription(context.getString(R.string.artista_imagen_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.artista_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.artista_fecha_nacimiento)))
    }

}