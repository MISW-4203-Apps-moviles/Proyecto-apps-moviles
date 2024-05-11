package com.miso.vinilos.e2e.page_object

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.navigation.NavController
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R
import org.junit.Assert

class NavigationSelectionPage (composeRule: ComposeTestRule, activity: MainActivity) :

    PageObject(composeRule) {

        val context: Context = activity.applicationContext

    fun assertCurrentRouteName(navController: NavController, expectedRouteName: String) {
            Assert.assertEquals(expectedRouteName, navController.currentBackStackEntry?.destination?.route)
        }
        //Navegación y comprobación de la pantalla de listado de álbumes
        fun clickAlbumes() =
            clickTextButton(context.getString(R.string.nav_albumes_label))
    }
