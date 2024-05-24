package com.miso.vinilos.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class DateHelper {
    companion object {
        fun formatDate(inputDate: String): String {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = inputFormat.parse(inputDate)
            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

            outputFormat.timeZone = TimeZone.getTimeZone("GMT-5")
            return date?.let { outputFormat.format(it) } + "-05:00"
        }

        fun generateRandomDateFromCurrentMonth(): String {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val randomDay = (1..lastDayOfMonth).random()

            calendar.set(Calendar.DAY_OF_MONTH, randomDay)

            val formatter = SimpleDateFormat("MMMM d", Locale.getDefault())
            return formatter.format(calendar.time) + ","
        }
    }
}