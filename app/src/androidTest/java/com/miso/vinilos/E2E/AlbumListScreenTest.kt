package com.miso.vinilos.E2E

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.E2E.page_object.AlbumListPage
import com.miso.vinilos.E2E.page_object.NavigationSelectionPage
import com.miso.vinilos.E2E.page_object.UserTypeSelectionPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.MainScreen
import com.miso.vinilos.R
import com.miso.vinilos.ui.theme.VinilosTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                navController = rememberNavController()
                VinilosTheme {
                    MainScreen(navController)
                }
            }
        }
    }

    @Test
    fun albumListScreenNavHost_validateContent_existTag() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            validateTag()
        }
    }

    @Test
    fun albumListScreenNavHost_validateContent_existRoute() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            assertCurrentRouteName(navController,"album_list")
        }
    }

    @Test
    fun albumListScreenNavHost_fetchContent_showResults() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
    }

    @Test
    fun albumListScreenNavHost_menuSelected_albumLabelIsSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            assertItemSelection(context.getString(R.string.nav_albumes_label))
        }
    }

    @Test
    fun albumListScreenNavHost_menuSelected_collectionsLabelIsNotSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            assertNotItemSelection(context.getString(R.string.nav_coleccionistas_label))
        }
    }

    @Test
    fun albumListScreenNavHost_menuSelected_performerLabelIsNotSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            assertNotItemSelection(context.getString(R.string.nav_artistas_label))
        }
    }

    @Test
    fun albumListScreenNavHost_albumListItem_albumListExists() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(AlbumListPage(composeTestRule, composeTestRule.activity)) {
            assertHasContentElement(context.getString(R.string.album_list_item_test))
        }
    }
}