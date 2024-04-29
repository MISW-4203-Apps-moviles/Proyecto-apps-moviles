package com.miso.vinilos.E2E

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import com.miso.vinilos.MainActivity
import com.miso.vinilos.MainScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.miso.vinilos.R
import com.miso.vinilos.E2E.page_object.NavigationSelectionPage
import com.miso.vinilos.E2E.page_object.UserTypeSelectionPage

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupMoodTrackerAppNavHost() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainScreen(navController = navController)
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
            verifyAlbumesLoad()
        }

    }
}

