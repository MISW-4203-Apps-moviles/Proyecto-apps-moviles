package com.miso.vinilos.e2e.page_object

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import org.junit.Assert

abstract class PageObject (val composeRule: ComposeTestRule) {

    companion object {
        private const val TIME_OUT = 10000L
    }

    fun clickTextButton(text: String, substring: Boolean = false) = composeRule
        .onNode(hasText(text, substring = substring), useUnmergedTree = true)
        .performClick()

    fun clickTextButtonByContetDescription(description: String, exactly: Boolean = true) = composeRule
        .onNode(if (exactly) hasContentDescriptionExactly(description) else hasContentDescription(description), useUnmergedTree = true)
        .performClick()

    fun clickTextFieldByContentDescription(description: String) {
        composeRule
            .onNode(hasContentDescription(description), useUnmergedTree = true)
            .performClick()
    }

    fun assertSemanticDescription(description: String) =
        composeRule
            .onNode(hasContentDescription(description), useUnmergedTree = true)
            .assertExists()

    fun assertText(text: String, ignoreCase: Boolean = false, substring: Boolean = false) =
        composeRule.onNode(
            matcher = hasText(text, ignoreCase = ignoreCase, substring = substring),
            useUnmergedTree = true
        )
       .assertExists()

    fun assertItemSelection(tag: String) =
        composeRule.onNodeWithText(tag).assertIsSelected()

    fun assertNotItemSelection(tag: String) =
        composeRule.onNodeWithText(tag).assertIsNotSelected()

    fun getNodeItemFromList(childIndex: Int, parentMatcher: SemanticsMatcher): SemanticsNodeInteraction {
        val node = composeRule
            .onAllNodes(
                parentMatcher,
                useUnmergedTree = true
            )[0].onChildAt(childIndex) // 0 is the AlbumItem
        return node
    }
    fun clickItemFromList(childIndex: Int, parentMatcher: SemanticsMatcher) {
        composeRule
            .onAllNodes(
                parentMatcher,
                useUnmergedTree = true
            )[0].onChildAt(childIndex) // 0 is the AlbumItem
            .performClick()
    }
    fun waitForElement(matcher: SemanticsMatcher) = composeRule.waitUntil(TIME_OUT) {
        composeRule
            .onAllNodes(matcher, useUnmergedTree = true)
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    fun waitForElementWithText(text: String, substring: Boolean = false) = composeRule.waitUntil(TIME_OUT) {
        composeRule
            .onAllNodes(hasText(text, substring = substring), useUnmergedTree = true)
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    fun waitForElementWithContentDescription(description: String) = composeRule.waitUntil(TIME_OUT) {
        composeRule
            .onAllNodes(hasContentDescription(description), useUnmergedTree = true)
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    fun selectVynilsDatePickerDateByContentDescription(
        description: String,
        date: String = "Today",
        okButtonText: String = "OK"
    ) {
        clickTextFieldByContentDescription(description)
        waitForElementWithText(date, true)
        clickTextButton(date, true)
        clickTextButton(okButtonText)
        //composeRule.onAllNodes(isRoot()).get(1).printToLog("T:")
    }

    @OptIn(ExperimentalTestApi::class)
    fun waitFor(matcher: SemanticsMatcher) = composeRule.waitUntilExactlyOneExists(matcher)

    fun assertCurrentRouteName(navController: NavController, expectedRouteName: String) {
        Assert.assertEquals(
            expectedRouteName,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    fun fillTextFieldByContentDescription(description: String, inputValue: String) {
        composeRule
            .onNodeWithContentDescription(description, useUnmergedTree = true)
            .performTextInput(inputValue)
    }

}