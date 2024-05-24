package com.miso.vinilos.e2e

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.MainActivity
import com.miso.vinilos.e2e.page_object.AlbumCreatePage
import com.miso.vinilos.e2e.page_object.AlbumListPage
import com.miso.vinilos.e2e.page_object.UserTypeSelectionPage
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.ui.theme.VinylsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumCreateTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                navController = rememberNavController()
                VinylsTheme {
                    VinylApp(navController)
                }
            }
        }
    }

    @Test
    fun albumCrateScreen_existsCreateButton() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            validateCreateAlbumButton()
        }
    }

    @Test
    fun albumCrateScreen_existsRoute() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            validateCreateAlbumButton()
            clickCreateAlbum()
            assertCurrentRouteName(navController, "AlbumCreate")
        }
    }

    @Test
    fun albumCrateScreen_validateScreen() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            validateCreateAlbumButton()
            clickCreateAlbum()
        }

        with(AlbumCreatePage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
        }
    }

    @Test
    fun albumCreateScreen_createAlbum_success() {
        val createdAlbumName:String

        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            validateCreateAlbumButton()
            clickCreateAlbum()
        }

        with(AlbumCreatePage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            createdAlbumName = fillAlbumData()
            clickCreateAlbum()
        }

        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            validateLoader()
            validateListElement()
            validateListItemElement()
            elementInListHasAlbumName(createdAlbumName)
        }
    }
}