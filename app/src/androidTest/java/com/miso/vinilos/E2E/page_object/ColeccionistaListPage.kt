package com.miso.vinilos.E2E.page_object

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R

class ColeccionistaListPage (composeRule: ComposeTestRule, activity: MainActivity) :
    PageObject(composeRule) {

    val context = activity.applicationContext

    fun validateLoader() {
        waitForElement(hasContentDescription(context.getString(R.string.loading_data)))
    }

    fun validateListElement() {
        waitForElement(hasContentDescription(context.getString(R.string.lista_coleccionistas_descripcion)))
    }

    fun validateListItemElement() {
        waitForElement(hasParent(hasContentDescription(context.getString(R.string.nombre_del_coleccionista))))
    }

    fun clickListElement(itemIndex: Int) {
        clickItemFromList(
            itemIndex,
            hasContentDescription(context.getString(R.string.nombre_del_coleccionista))
        )
    }

    fun getListItemFromList(itemIndex: Int): SemanticsNodeInteraction {
        return getNodeItemFromList(
            itemIndex,
            hasContentDescription(context.getString(R.string.lista_artistas_descripcion))
        )
    }
    fun getArtistaNameFromList(itemIndex:Int):String {
        return getListItemFromList(itemIndex)
            .onChildren()
            .filter(hasContentDescription(context.getString(R.string.album_artista_descripcion)))
            .onFirst()
            .onChildren()
            .filter(hasContentDescription(context.getString(R.string.performer_click)))
            .onFirst()
            .fetchSemanticsNode()
            .config
            .getOrNull(SemanticsProperties.Text)?.firstOrNull()?.text ?: ""
    }

}