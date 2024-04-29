package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.navigation.NavController
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R
import org.junit.Assert

class NavigationSelectionPage (composeRule: ComposeTestRule, activity: MainActivity) :

    PageObject(composeRule) {

        val context = activity.applicationContext

        fun NavController.assertCurrentRouteName(expectedRouteName: String) {
            Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
        }

    }
