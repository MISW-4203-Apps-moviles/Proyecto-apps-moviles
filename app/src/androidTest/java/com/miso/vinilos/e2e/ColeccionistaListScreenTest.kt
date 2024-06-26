package com.miso.vinilos.e2e

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.e2e.page_object.ColeccionistaListPage
import com.miso.vinilos.MainActivity
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.R
import com.miso.vinilos.e2e.page_object.NavigationSelectionPage
import com.miso.vinilos.e2e.page_object.UserTypeSelectionPage
import com.miso.vinilos.ui.theme.VinylsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ColeccionistaListScreenTest {

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
    fun coleccionistaList_validateContent_existRoute() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickColeccionistas()
            assertCurrentRouteName(navController,"ColeccionistasList")
        }
    }

    @Test
    fun coleccionistaList_menuSelected_collecLabelIsSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickColeccionistas()
            assertCurrentRouteName(navController,"ColeccionistasList")
        }
        with(ColeccionistaListPage(composeTestRule, composeTestRule.activity)) {
            assertItemSelection(context.getString(R.string.nav_coleccionistas_label))
        }
    }

    @Test
    fun coleccionistaList_menuSelected_albumsLabelIsNotSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickColeccionistas()
            assertCurrentRouteName(navController,"ColeccionistasList")
        }
        with(ColeccionistaListPage(composeTestRule, composeTestRule.activity)) {
            assertNotItemSelection(context.getString(R.string.nav_albumes_label))
        }
    }

    @Test
    fun coleccionistaList_menuSelected_artistLabelIsNotSelected() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickColeccionistas()
            assertCurrentRouteName(navController,"ColeccionistasList")
        }
        with(ColeccionistaListPage(composeTestRule, composeTestRule.activity)) {
            assertNotItemSelection(context.getString(R.string.nav_artistas_label))
        }
    }

    @Test
    fun coleccionistaList_albumListItem_click_success() {
        with(UserTypeSelectionPage(composeTestRule, composeTestRule.activity)) {
            validateScreen()
            clickPublicUserTypeButton()
        }
        with(NavigationSelectionPage(composeTestRule, composeTestRule.activity)) {
            clickColeccionistas()
            assertCurrentRouteName(navController,"ColeccionistasList")
        }

        with(ColeccionistaListPage(composeTestRule, composeTestRule.activity)) {

            // El contenedor del listado está presente
            validateListElement()

            // Los elementos de la lista están presentes
            validateListItemElement()

            // Click en el primer elemento de la lista
            clickListElement(0)
        }
    }
}