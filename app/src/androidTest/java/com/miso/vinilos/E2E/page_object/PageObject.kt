package com.miso.vinilos.E2E.page_object


import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick


abstract class PageObject (val composeRule: ComposeTestRule) {

    fun clickTextButton(text: String) = composeRule.onNode(hasTextExactly(text)).performClick()

    fun assertImage(description: String) =
        composeRule.onNode(hasContentDescription(description)).assertExists()

    fun assertText(text: String, ignoreCase: Boolean = false, substring: Boolean = false) =
        composeRule.onNode(hasText(text, ignoreCase = ignoreCase, substring = substring))
            .assertExists()

    fun assertShowItemsSize(element: String, size: Int) =
        composeRule.onAllNodes(hasTextExactly(element)).assertCountEquals(size)

    fun assertItemSelection(tag: String) =
        composeRule.onNodeWithText(tag).assertIsSelected()

    fun assertNotItemSelection(tag: String) =
        composeRule.onNodeWithText(tag).assertIsNotSelected()

    fun assertTagExists(text: String)=
        composeRule
            .onNodeWithTag(text)
            .assertExists()

    fun assertHasContentElement(tag: String) {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(tag).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    fun waitFor(matcher: SemanticsMatcher) = composeRule.waitUntilExactlyOneExists(matcher)
}