package com.miso.vinilos.E2E.page_object


import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction

abstract class PageObject (val composeRule: ComposeTestRule) {

    fun clickTextButton(text: String) = composeRule.onNode(hasTextExactly(text)).performClick()

    fun clickNodeTextButton(text: String) = composeRule.onNodeWithText(text).performClick()

    fun assertImage(description: String) =
        composeRule.onNode(hasContentDescription(description)).assertExists()

    fun assertText(text: String, ignoreCase: Boolean = false, substring: Boolean = false) =
        composeRule.onNode(hasText(text, ignoreCase = ignoreCase, substring = substring))
            .assertExists()

    fun assertTagExists(text: String)=
        composeRule
            .onNodeWithTag(text)
            .assertExists()

    fun waitUntilLoaded() =
        composeRule.waitUntil(10000) {
            composeRule
                .onAllNodes(hasTestTag("AlbumList"))
                .fetchSemanticsNodes().size == 1
        }

    fun clickListElement() =
        composeRule.onAllNodesWithTag("Item")
            .filter(hasClickAction())
            .apply {
                fetchSemanticsNodes().forEachIndexed { i, _ ->
                    get(i).performSemanticsAction(SemanticsActions.OnClick)
                }
            }
    @OptIn(ExperimentalTestApi::class)
    fun waitFor(matcher: SemanticsMatcher) = composeRule.waitUntilExactlyOneExists(matcher)
}