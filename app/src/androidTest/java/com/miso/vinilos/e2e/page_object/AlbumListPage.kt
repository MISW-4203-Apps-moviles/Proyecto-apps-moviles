package com.miso.vinilos.e2e.page_object

import android.content.Context
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performScrollToNode
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R


class AlbumListPage (composeRule: ComposeTestRule, activity: MainActivity) :
    PageObject(composeRule) {

    val context: Context = activity.applicationContext

    fun validateLoader() {
        waitFor(hasContentDescription(context.getString(R.string.loading_data)))
    }

    fun validateListElement() {
        waitForElement(hasContentDescription(context.getString(R.string.lista_albumes_descripcion)))
    }

    fun validateListItemElement() {
        waitForElement(hasParent(hasContentDescription(context.getString(R.string.lista_albumes_descripcion))))
    }

    fun validateCreateAlbumButton() {
        waitForElementWithText(context.getString(R.string.agregar_album))
    }

    fun clickListElement(itemIndex: Int) {
        clickItemFromList(
            itemIndex,
            hasContentDescription(context.getString(R.string.lista_albumes_descripcion))
        )
    }

    fun elementInListHasAlbumName(albumName: String) {
        composeRule.onNode(
            matcher = hasContentDescription(context.getString(R.string.lista_albumes_descripcion)),
            useUnmergedTree = true
        ).performScrollToNode(hasText(albumName))
    }

    fun getListItemFromList(itemIndex: Int): SemanticsNodeInteraction {
        return getNodeItemFromList(
            itemIndex,
            hasContentDescription(context.getString(R.string.lista_albumes_descripcion))
        )
    }

    fun getAlbumNameFromList(itemIndex:Int):String {
        return getListItemFromList(itemIndex)
            .onChildren()
            .filter(hasContentDescription(context.getString(R.string.album_nombre_descripcion)))
            .onFirst()
            .fetchSemanticsNode()
            .config
            .getOrNull(SemanticsProperties.Text)?.firstOrNull()?.text ?: ""
    }

    fun clickCreateAlbum() =
        clickTextButton(context.getString(R.string.agregar_album))
}