package com.miso.vinilos.e2e.page_object

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

abstract class PageObject (val composeRule: ComposeTestRule) {

    fun clickTextButton(text: String) = composeRule.onNode(hasTextExactly(text)).performClick()

    fun assertSemanticDescription(description: String) =
        composeRule.onNode(hasContentDescription(description)).assertExists()

    fun assertText(text: String, ignoreCase: Boolean = false, substring: Boolean = false) =
        composeRule.onNode(hasText(text, ignoreCase = ignoreCase, substring = substring))
            .assertExists()

    fun assertItemSelection(tag: String) =
        composeRule.onNodeWithText(tag).assertIsSelected()

    fun assertNotItemSelection(tag: String) =
        composeRule.onNodeWithText(tag).assertIsNotSelected()

    fun assertTagExists(text: String)=
        composeRule
            .onNodeWithTag(text)
            .assertExists()

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
    fun waitForElement(matcher: SemanticsMatcher) = composeRule.waitUntil(7000) {
        composeRule
            .onAllNodes(matcher, useUnmergedTree = true)
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    @OptIn(ExperimentalTestApi::class)
    fun waitFor(matcher: SemanticsMatcher) = composeRule.waitUntilExactlyOneExists(matcher)
}