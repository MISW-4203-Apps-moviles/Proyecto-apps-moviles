package com.miso.vinilos.e2e.page_object

import android.content.Context
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class ColeccionistaDetailPage(composeRule: ComposeTestRule, activity: MainActivity):
    PageObject(composeRule) {

    val context: Context = activity.applicationContext

    fun validateScreen() {
        waitForElement(hasContentDescription(context.getString(R.string.desc_nombre_del_coleccionista)))
        waitForElement(hasContentDescription(context.getString(R.string.desc_email_del_coleccionista)))
        waitForElement(hasContentDescription(context.getString(R.string.desc_telefono_del_coleccionista)))

    }

}