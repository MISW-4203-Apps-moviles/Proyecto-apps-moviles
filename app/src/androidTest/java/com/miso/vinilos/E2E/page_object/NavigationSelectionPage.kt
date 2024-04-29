package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.navigation.NavController
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R
import org.junit.Assert

class NavigationSelectionPage (composeRule: ComposeTestRule, activity: MainActivity) :

    PageObject(composeRule) {

        val context = activity.applicationContext
        //Navegación y comprobación de la pantalla de listado de álbumes
        fun clickAlbumes() =
            clickTextButton(context.getString(R.string.nav_albumes_label))

        fun verifyAlbumesLoad() {
            assertTagExists("AlbumList")
            waitUntilLoaded()
        }


    }
