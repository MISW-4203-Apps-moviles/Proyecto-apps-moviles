package com.miso.vinilos.e2e

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.miso.vinilos.e2e.page_object.NavigationSelectionPage
import com.miso.vinilos.e2e.page_object.UserTypeSelectionPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.ui.VinylApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            VinylApp(navController = navController)
        }
    }

    @Test
    fun navigation_verifyAlbumsPage(){

        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }

        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickAlbumes()
        }

    }
}

