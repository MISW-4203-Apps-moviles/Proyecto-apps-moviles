package com.miso.vinilos.e2e.page_object

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.miso.vinilos.MainActivity
import com.miso.vinilos.R
import com.miso.vinilos.e2e.utils.TestUtils
import com.miso.vinilos.models.Genre
import com.miso.vinilos.models.RecordLabel
import com.miso.vinilos.utils.DateHelper
import io.github.serpro69.kfaker.Faker


class AlbumCreatePage (composeRule: ComposeTestRule, activity: MainActivity) :
    PageObject(composeRule) {

    val context: Context = activity.applicationContext
    private val faker = Faker()

    fun validateScreen() {
        waitForElementWithText(context.getString(R.string.nombre))
        waitForElementWithText(context.getString(R.string.portada))
        waitForElementWithText(context.getString(R.string.descripcion))
        waitForElementWithText(context.getString(R.string.genero))
        waitForElementWithText(context.getString(R.string.sello_discografico))
        waitForElementWithText(context.getString(R.string.fecha_de_lanzamiento))
        waitForElementWithText(context.getString(R.string.agregar_album))
    }

    fun fillAlbumData():String {
        val albumName = faker.music.albums()
        val albumDescription = faker.backToTheFuture.quotes()
        val albumCover = "https://loremflickr.com/320/240/music"
        val albumGenre = TestUtils.getRandomEnumProperty(Genre::class, "displayName")
        val albumRecordLabel = TestUtils.getRandomEnumProperty(RecordLabel::class, "displayName")

        fillTextFieldByContentDescription(context.getString(R.string.nombre), albumName)
        fillTextFieldByContentDescription(context.getString(R.string.portada), albumCover)

        val randomDate = DateHelper.generateRandomDateFromCurrentMonth()

        selectVynilsDatePickerDateByContentDescription(
            description = context.getString(R.string.fecha_de_lanzamiento),
            date = randomDate,
            okButtonText = context.getString(R.string.aceptar)
        )
        fillTextFieldByContentDescription(context.getString(R.string.genero), albumGenre)
        fillTextFieldByContentDescription(context.getString(R.string.descripcion), albumDescription)
        fillTextFieldByContentDescription(context.getString(R.string.sello_discografico), albumRecordLabel)

        return albumName
    }

    fun clickCreateAlbum() =
        clickTextButton(context.getString(R.string.agregar_album))
}