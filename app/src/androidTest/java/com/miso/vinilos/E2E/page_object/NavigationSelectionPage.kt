package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.miso.vinilos.MainActivity
import org.junit.Assert
import com.miso.vinilos.R

class NavigationSelectionPage (composeRule: ComposeTestRule, activity: MainActivity) :

    PageObject(composeRule) {

        val context = activity.applicationContext
        private lateinit var navController: NavHostController

        fun assertCurrentRouteName(navController: NavController, expectedRouteName: String) {
            Assert.assertEquals(expectedRouteName, navController.currentBackStackEntry?.destination?.route)
        }
        //Navegación y comprobación de la pantalla de listado de álbumes
        fun clickAlbumes() =
            clickTextButton(context.getString(R.string.nav_albumes_label))
        //Navegaciòn pantalla de artistas
        fun clickArtistas() =
            clickTextButton(context.getString(R.string.nav_artistas_label))
    }
