package com.sinamtd.sixappcompose.utils


import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.sinamtd.sixappcompose.R

fun getWeatherDescription(code: Int?): String{
    return when(code){
        0 -> "Sunny"
        1,2 -> "Partly Cloudy"
        3 -> "Cloudy"
        45,48 -> "Foggy"
        51,53,55 -> "Light Rain"
        61,63,65 -> "Rain"
        71,73,75 -> "Snow"
        80,81,82 -> "Showers"
        95,96,99 -> "Thunderstorm"
        else -> "Variable"

    }
}
fun getWeatherEmoji(code: Int): String {
    return when (code) {
        0 -> "☀️"
        1, 2 -> "⛅"
        3 -> "☁️"
        45, 48 -> "🌫"
        51, 53, 55 -> "🌦"
        61, 63, 65 -> "🌧"
        71, 73, 75 -> "❄️"
        80, 81, 82 -> "🌧"
        95, 96, 99 -> "⛈"
        else -> "🌈"
    }
}
@DrawableRes
fun getImage(code: Int?,isDay: Int?): Int{
    if (code==null) return R.drawable.night
    val isDaytime = isDay == 1
    return when (code) {
        0,1,2 -> if (isDaytime) R.drawable.day else R.drawable.night
        3-> if(isDaytime) R.drawable.cloudyday else R.drawable.cloudnight
        45,48-> if (isDaytime) R.drawable.foggyday else R.drawable.foggynight
        51,53,55,61,63,65-> if (isDaytime) R.drawable.rainday else R.drawable.rainynaight
        71,73,75 -> if (isDaytime) R.drawable.snowday else R.drawable.snownight
        80,81,82,95,96,99-> if(isDaytime) R.drawable.showerday else R.drawable.showernight
        else -> R.drawable.night


    }
}

fun getAqiLevel(code: Int): String{
    return when(code){
        in 0..50->"Good"
        in 51..100-> "Moderate"
        in 101..150-> "Unhealthy for Sensitive Group"
        in 151..200-> " Unhealthy"
        in 201..450-> "Very Unhealthy"
        else -> "Hazardous"

    }
}

fun getAqiColor(code: Int?): androidx.compose.ui.graphics.Color{
    return when(code){
        in 0..50-> Color(0xFF00E400)
        in 51..100-> Color(0xFFFFFF00)
        in 101..150-> Color(0xFFFF7E00)
        in 151..200-> Color(0xFFFF0000)
        in 201..450-> Color(0xFF8F3F97)
        else -> Color(0xFF7E0023)

    }
}



fun String.toDayOfWeek(): String {
    return try {
        val date = java.time.LocalDate.parse(this)
        when (date.dayOfWeek) {
            java.time.DayOfWeek.MONDAY -> "Mon"
            java.time.DayOfWeek.TUESDAY -> "Teu"
            java.time.DayOfWeek.WEDNESDAY -> "Wed"
            java.time.DayOfWeek.THURSDAY -> "Thu"
            java.time.DayOfWeek.FRIDAY -> "Fri"
            java.time.DayOfWeek.SATURDAY -> "Sat"
            java.time.DayOfWeek.SUNDAY -> "Sun"
            else -> ""
        }
    } catch (_: Exception) {
        "---"
    }
}

fun String.toPersianTime(): String {
    return try {
        val parts = this.split("T")
        if (parts.size==2){
            val timeParts=parts[1].split(":")
            if (timeParts.size>=2){
                "${timeParts[0]} : ${timeParts[1]}"
            }
            else{this}

        } else {
            this
        }
    } catch (_: Exception) {
        "---"
    }
}

fun String.toDayOfWeekTemp(): String {
    return try {
        val date = java.time.LocalDate.parse(this)
        when (date.dayOfWeek) {
            java.time.DayOfWeek.MONDAY -> "     Mon"
            java.time.DayOfWeek.TUESDAY -> "     Teu"
            java.time.DayOfWeek.WEDNESDAY -> "     Wed"
            java.time.DayOfWeek.THURSDAY -> "     Thu"
            java.time.DayOfWeek.FRIDAY -> "     Fri"
            java.time.DayOfWeek.SATURDAY -> "     Sat"
            java.time.DayOfWeek.SUNDAY -> "     Sun"
            else -> ""
        }
    } catch (_: Exception) {
        "---"
    }
}

fun charNameWeek(selected:Int): String{

   return when(selected){
        0-> "Temperature for the next 7 days 📅"
        1-> "WindSpeed for the next 7 days 📅"
        2-> "Precipitation for the next 7 days 📅"
         else -> ""
    }
}

fun charNameDay(selected:Int): String{

    return when(selected){
        0-> "Temperature for the next 24 hours 🌡️"
        1-> "Uv for the next 24 hours ☀️️"
        2->"Humidity for the next 24 hours 💧️"
        else -> ""
    }
}