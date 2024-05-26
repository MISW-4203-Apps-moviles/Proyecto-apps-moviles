package com.miso.vinilos.e2e.page_object

import android.content.Context
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

    val context: Context = activity.applicationContext

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
            hasContentDescription(context.getString(R.string.lista_coleccionistas_descripcion))
        )
    }
   /* fun getColeccionistaNameFromList(itemIndex:Int):String {
        return getListItemFromList(itemIndex)
            .onChildren()
            .filter(hasContentDescription(context.getString(R.string.nombre_del_coleccionista)))
            .onFirst()
            .onChildren()
            .filter(hasContentDescription(context.getString(R.string.click_coleccionista)))
            .onFirst()
            .fetchSemanticsNode()
            .config
            .getOrNull(SemanticsProperties.Text)?.firstOrNull()?.text ?: ""
    }*/

    fun getColeccionistaNameFromList(itemIndex:Int):String {
        val nodes = composeRule.onAllNodes(hasContentDescription(context.getString(R.string.nombre_del_coleccionista)))
        val node = nodes[0]

        return node.fetchSemanticsNode().config[SemanticsProperties.Text][itemIndex].text

    }

}