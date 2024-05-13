package com.miso.vinilos.e2e.page_object

import android.content.Context
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class ArtistaDetailPage(composeRule: ComposeTestRule, activity: MainActivity):
    PageObject(composeRule) {

    val context: Context = activity.applicationContext

    fun validateScreen() {
        waitForElement(hasContentDescription(context.getString(R.string.artista_nombre)))
        waitForElement(hasContentDescription(context.getString(R.string.artista_imagen_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.artista_descripcion)))
        waitForElement(hasContentDescription(context.getString(R.string.artista_fecha_nacimiento)))
    }

}