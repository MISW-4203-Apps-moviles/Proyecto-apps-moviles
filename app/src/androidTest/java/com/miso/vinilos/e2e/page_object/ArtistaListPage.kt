package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class ArtistaListPage (composeRule: ComposeTestRule, activity: MainActivity) :
    PageObject(composeRule) {

    val context = activity.applicationContext

    fun validateLoader() {
        waitForElement(hasContentDescription(context.getString(R.string.loading_data)))
    }

    fun validateListElement() {
        waitForElement(hasContentDescription(context.getString(R.string.lista_artistas_descripcion)))
    }

    fun validateListItemElement() {
        waitForElement(hasParent(hasContentDescription(context.getString(R.string.album_artista_descripcion))))
    }

    fun clickListElement(itemIndex: Int) {
        clickItemFromList(
            itemIndex,
            hasContentDescription(context.getString(R.string.album_artista_descripcion))
        )
    }

    fun getArtistaNameFromList(itemIndex:Int):String {
        val nodes = composeRule.onAllNodes(hasContentDescription(context.getString(R.string.album_artista_descripcion)))
        val node = nodes[0]

        return node.fetchSemanticsNode().config[SemanticsProperties.Text][itemIndex].text

    }

}