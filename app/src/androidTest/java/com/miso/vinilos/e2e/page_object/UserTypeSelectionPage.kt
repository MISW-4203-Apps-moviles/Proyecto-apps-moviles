package com.miso.vinilos.e2e.page_object

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class UserTypeSelectionPage(composeRule: ComposeTestRule, activity: MainActivity) :
    PageObject(composeRule) {

    val context: Context = activity.applicationContext

    fun validateScreen() {
        assertText(context.getString(R.string.tipo_usuario_titulo))
        assertSemanticDescription(context.getString(R.string.vinilos_logo_descripcion))
    }

    fun clickCollectionUserTypeButton() =
        clickTextButton(context.getString(R.string.tipo_usuario_coleccionista))

    fun clickPublicUserTypeButton() =
        clickTextButton(context.getString(R.string.tipo_usuario_visitante))
}