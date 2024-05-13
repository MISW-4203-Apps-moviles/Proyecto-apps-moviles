package com.miso.vinilos.e2e

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.MainActivity
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.R
import com.miso.vinilos.e2e.page_object.ArtistaListPage
import com.miso.vinilos.e2e.page_object.NavigationSelectionPage
import com.miso.vinilos.e2e.page_object.UserTypeSelectionPage
import com.miso.vinilos.ui.theme.VinylsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtistasListScreenTest {

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
    fun artistasListScreenNavHost_validateContent_existRoute() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickArtistas()
            assertCurrentRouteName(navController,"ArtistasList")
        }
    }

    @Test
    fun artistasListScreenNavHost_menuSelected_artistaLabelIsSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickArtistas()
            assertCurrentRouteName(navController,"ArtistasList")
        }
        with(ArtistaListPage(composeTestRule, composeTestRule.activity)) {
            assertItemSelection(context.getString(R.string.nav_artistas_label))
        }
    }

    @Test
    fun artistasListScreenNavHost_menuSelected_collectionsLabelIsNotSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickArtistas()
            assertCurrentRouteName(navController,"ArtistasList")
        }
        with(ArtistaListPage(composeTestRule, composeTestRule.activity)) {
            assertNotItemSelection(context.getString(R.string.nav_coleccionistas_label))
        }
    }

    @Test
    fun artistasListScreenNavHost_menuSelected_albumLabelIsNotSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickArtistas()
            assertCurrentRouteName(navController,"ArtistasList")
        }
        with(ArtistaListPage(composeTestRule, composeTestRule.activity)) {
            assertNotItemSelection(context.getString(R.string.nav_albumes_label))
        }
    }

    @Test
    fun artistasListScreenNavHost_albumListItem_click_success() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickArtistas()
            assertCurrentRouteName(navController,"ArtistasList")
        }

        with(ArtistaListPage(composeTestRule, composeTestRule.activity)) {

            // Spinning loader está presente
            validateLoader()

            // El contenedor del listado está presente
            validateListElement()

            // Los elementos de la lista están presentes
            validateListItemElement()

            // Click en el primer elemento de la lista
            clickListElement(0)
        }
    }
}