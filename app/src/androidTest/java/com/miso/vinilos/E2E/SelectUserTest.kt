package com.miso.vinilos.E2E

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.miso.vinilos.E2E.page_object.UserTypeSelectionPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.ui.theme.VinylsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SelectUserTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            VinylsTheme {
                VinylApp()
            }
        }
    }

    @Test
    fun mainScreenNavHost_validateContent_existImageAndButton() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
        }
    }

    @Test
    fun mainScreenNavHost_clickCollectionUserButton_navigatesToAlbumList() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickCollectionUserTypeButton()
        }
    }

    @Test
    fun mainScreenNavHost_clickPublicUserButton_navigatesToAlbumList() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
    }
}