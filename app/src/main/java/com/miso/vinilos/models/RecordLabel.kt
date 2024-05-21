package com.miso.vinilos.models

enum class RecordLabel(val displayName: String) {
    SONY_MUSIC("Sony Music"),
    EMI("EMI"),
    DISCOS_FUENTES("Discos Fuentes"),
    ELEKTRA("Elektra"),
    FANIA_RECORDS("Fania Records");

    companion object {
        val getRecordLabels = entries
    }
}