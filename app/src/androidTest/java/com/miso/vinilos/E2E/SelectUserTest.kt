package com.miso.vinilos.E2E

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.miso.vinilos.E2E.page_object.UserTypeSelectionPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.MainScreen
import com.miso.vinilos.ui.theme.VinilosTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SelectUserTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            VinilosTheme {
                MainScreen()
            }
        }
    }

    @Test
    fun testUserTypeSelectionSuccess() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validarPantalla()
            clickColeccionista()
        }
    }
}