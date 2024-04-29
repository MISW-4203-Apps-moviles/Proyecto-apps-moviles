package com.miso.vinilos.E2E

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.miso.vinilos.E2E.page_object.AlbumDetailPage
import com.miso.vinilos.E2E.page_object.NavigationSelectionPage
import com.miso.vinilos.E2E.page_object.UserTypeSelectionPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.MainScreen
import com.miso.vinilos.ui.theme.VinilosTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumDetailTest {

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
    fun verifyAlbumDetailPageNavigation(){

        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {

            validateScreen()
            clickCollectionUserTypeButton()
        }

        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
           clickAlbumes()
            verifyAlbumesLoad()

        }

        with(AlbumDetailPage(composeTestRule, composeTestRule.activity)) {
            verifyAlbumesDetalleLoad()
        }

    }

}