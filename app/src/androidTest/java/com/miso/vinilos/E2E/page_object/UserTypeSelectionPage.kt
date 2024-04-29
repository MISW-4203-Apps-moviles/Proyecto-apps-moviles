package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class UserTypeSelectionPage(composeRule: ComposeTestRule, activity: MainActivity) :
    PageObject(composeRule) {

    val context = activity.applicationContext

    fun validateScreen() {
        assertText(context.getString(R.string.tipo_usuario_titulo))
        assertImage(context.getString(R.string.vinilos_logo_descripcion))
    }

    fun clickCollectionUserTypeButton() =
        clickTextButton(context.getString(R.string.tipo_usuario_coleccionista))

    fun clickPublicUserTypeButton() =
        clickTextButton(context.getString(R.string.tipo_usuario_visitante))

    fun validateTag() =
        assertTagExists(context.getString(R.string.user_type_selection_screen_test))
}