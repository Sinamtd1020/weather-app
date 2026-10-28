package com.sinamtd.sixappcompose.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sinamtd.sixappcompose.data.remote.dto.HourlyWeather
import com.sinamtd.sixappcompose.presentation.navigation.Screens
import com.sinamtd.sixappcompose.presentation.viewmodel.WeatherViewModel
import com.sinamtd.sixappcompose.utils.getAqiColor
import com.sinamtd.sixappcompose.utils.getAqiLevel
import com.sinamtd.sixappcompose.utils.getWeatherDescription
import com.sinamtd.sixappcompose.utils.getWeatherEmoji
import com.sinamtd.sixappcompose.utils.toPersianTime

@Composable
fun DetailScreen(viewModel: WeatherViewModel= hiltViewModel(), navController: NavController){
    val state by viewModel.state.collectAsState()
    val city=state.selectedCity?:""
    val favorites by viewModel.favorites.collectAsState()







    when{
        state.isLoading->{
            Box(modifier = Modifier.fillMaxSize().systemBarsPadding(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()}
        }
        state.error.isNotEmpty()->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(state.error, fontSize = 8.sp)}
        }
        state.weather!=null -> {

            val weather=state.weather
            val airQuality=state.airQuality
            val current=weather?.current
            val daily=weather?.daily
            val hourly=weather?.hourly
            val aqi=airQuality?.current





                 Column (modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF152644),Color(0xFF010f1c))))){
                     Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart){
                         IconButton(onClick = {navController.navigate(Screens.Main.rout) {
                             popUpTo(
                                 Screens.Main.rout
                             ) { inclusive = true }
                         }})
                         { Icon(Icons.Filled.ArrowBack,"", modifier = Modifier.size(35.dp), tint = Color.White) }}
                 Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                     Column(horizontalAlignment = Alignment.CenterHorizontally) {
                         Text(
                             text = "${current?.temperature_2m?.toInt() ?: "- -"}° ",
                             color = Color.White,
                             fontSize = 70.sp,
                             fontWeight = FontWeight.Bold,
                             modifier = Modifier.padding(start = 34.dp, top = 8.dp)
                         )
                         Spacer(Modifier.height(4.dp))
                         Text(
                             state.selectedCity?:"Tehran",
                             fontSize = 25.sp,
                             color = Color.White,
                             fontWeight = FontWeight.Bold,
                             modifier = Modifier.padding(start = 8.dp)
                         )
                     }
                     Spacer(Modifier.width(20.dp))
                     Text(text = getWeatherDescription(current?.weather_code), fontSize = 20.sp, color=Color.White)
                     Spacer(Modifier.width(20.dp))
                     Text(text = getWeatherEmoji(current?.weather_code?: 0), modifier = Modifier.fillMaxWidth(), fontSize = 30.sp, textAlign = TextAlign.Center, color = Color.White)
                 }
                     Spacer(Modifier.height(15.dp))
                     Card(modifier = Modifier.fillMaxWidth().height(240.dp)
                         ,elevation = CardDefaults.cardElevation(8.dp)
                         , colors = CardDefaults.cardColors(containerColor = Color(0xFF09131f)), shape = RoundedCornerShape(16.dp)){
                 Column(modifier = Modifier.fillMaxWidth()) {
                     Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Absolute.Left) {
                         Spacer(Modifier.width(10.dp))
                         Icon(Icons.Outlined.AccessTime,"", modifier = Modifier.size(25.dp), tint = Color.White)
                         Spacer(Modifier.width(4.dp))
                         Text("hourly forecast",  fontSize = 14.sp, color = Color.White)

                     }

                     Spacer(Modifier.height(10.dp))
                     HourWeather(hourly)
                     Spacer(Modifier.height(15.dp))
                     Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                     TextButton(
                         onClick = {
                             navController.navigate(Screens.Charts.rout) {
                                 popUpTo(
                                     Screens.Details.rout
                                 ) { inclusive = true }
                             }
                         }, modifier = Modifier.width(150.dp).height(35.dp)
                             .background(
                                 color = Color(0x888A8D8D),
                                 shape = RoundedCornerShape(12.dp)
                             )
                     )
                     { Text("See Charts", fontSize = 12.sp, color = Color.White) }}

                 }
                 }
                     Spacer(Modifier.height(10.dp))
                     Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                         , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
                     Column() {
                         Card(modifier = Modifier.width(125.dp).height(125.dp)
                             ,elevation = CardDefaults.cardElevation(8.dp)
                             , colors = CardDefaults.cardColors(containerColor = Color(0xFF09131f)), shape = RoundedCornerShape(16.dp)){
                             Column() {
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.Center,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Spacer(Modifier.height(10.dp))
                                     Icon(Icons.Outlined.Speed, "", modifier = Modifier.size(15.dp), tint = Color.White)
                                     Spacer(Modifier.width(4.dp))
                                     Text(text = "wind speed", fontSize = 14.sp, color = Color.White)

                                 }
                                 Spacer(Modifier.height(40.dp))
                                 Text(text = "km/h ${current?.wind_speed_10m} ", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White)
                             }}
                         Spacer(Modifier.height(10.dp))
                         Card(modifier = Modifier.width(125.dp).height(125.dp)
                             ,elevation = CardDefaults.cardElevation(8.dp)
                             , colors = CardDefaults.cardColors(containerColor = Color(0xFF09131f)), shape = RoundedCornerShape(16.dp)){
                             Column() {
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.Center,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Spacer(Modifier.height(8.dp))
                                     Icon(Icons.Outlined.Thermostat, "", modifier = Modifier.size(15.dp), tint = Color.White)
                                     Spacer(Modifier.width(4.dp))
                                     Text(text = "daily temp", fontSize = 14.sp, color = Color.White)

                                 }
                                 Spacer(Modifier.height(30.dp))
                                 Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                                     verticalAlignment = Alignment.CenterVertically) {
                                 Text(text = "max tempe :", fontSize = 12.sp, color = Color.White)
                                 Text(text = " ${daily?.temperature_2m_max[0]} ",fontSize = 12.sp, color = Color.White)}
                                 Spacer(Modifier.height(15.dp))
                                 Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                                     verticalAlignment = Alignment.CenterVertically) {
                                     Text(text = "min temp :", fontSize = 12.sp, color = Color.White)
                                     Text(text = " ${daily?.temperature_2m_min[0]} ",fontSize = 12.sp, color = Color.White)}
                             }}

                     }
                     Card(modifier = Modifier.width(125.dp).height(260.dp), elevation = CardDefaults.cardElevation(8.dp)
                         , colors = CardDefaults.cardColors(containerColor = Color(0xFF09131f)), shape = RoundedCornerShape(16.dp)){
                         Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {

                             Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                 Text("Humidity", fontSize = 10.sp, color = Color.LightGray)
                                 Text(text = "% ${current?.relative_humidity_2m}",fontSize = 10.sp, color = Color.LightGray)

                             }
                             Spacer(Modifier.height(4.dp))
                             Box(modifier = Modifier.fillMaxWidth().height(2.dp)
                                 .background(Brush.horizontalGradient(listOf(Color.Transparent,Color.LightGray,Color.Transparent))))

                             Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                 Text("Feel like", fontSize = 10.sp, color = Color.LightGray)
                                 Text(text = "${current?.apparent_temperature}",fontSize = 10.sp, color = Color.LightGray)
                             }
                             Spacer(Modifier.height(4.dp))
                             Box(modifier = Modifier.fillMaxWidth().height(2.dp)
                                 .background(Brush.horizontalGradient(listOf(Color.Transparent,Color.LightGray,Color.Transparent))))


                             Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                 Text("Chance of rain", fontSize = 10.sp, color = Color.LightGray)
                                 Text(text = "% ${daily?.precipitation_probability_mean[0]}",fontSize = 10.sp, color = Color.LightGray)

                             }
                             Spacer(Modifier.height(4.dp))
                             Box(modifier = Modifier.fillMaxWidth().height(2.dp)
                                 .background(Brush.horizontalGradient(listOf(Color.Transparent,Color.LightGray,Color.Transparent))))


                             Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                 Text("precipitation", fontSize = 10.sp, color = Color.LightGray)
                                 Text(text = "${current?.precipitation} ",fontSize = 10.sp, color = Color.LightGray)

                             }
                             Spacer(Modifier.height(4.dp))
                             Box(modifier = Modifier.fillMaxWidth().height(2.dp)
                                 .background(Brush.horizontalGradient(listOf(Color.Transparent,Color.LightGray,Color.Transparent))))


                             Row(modifier = Modifier.fillMaxWidth().padding(horizontal =10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                 Text("UV", fontSize = 10.sp, color = Color.LightGray)
                                 Text(text = "${current?.uv_index} ",fontSize = 10.sp, color = Color.LightGray)

                             }
                             Spacer(Modifier.height(4.dp))
                             Box(modifier = Modifier.fillMaxWidth().height(2.dp)
                                 .background(Brush.horizontalGradient(listOf(Color.Transparent,Color.LightGray,Color.Transparent))))


                             Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                 Text("Pressure", fontSize = 10.sp, color = Color.LightGray)
                                 Text(text = "${current?.surface_pressure} ",fontSize = 10.sp, color = Color.LightGray)

                             }
                             Spacer(Modifier.height(4.dp))


                         }
                     }

                 }

                     Spacer(Modifier.height(10.dp))

                     Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                     Card(modifier = Modifier.width(356.dp).height(148.dp).padding(vertical = 12.dp),elevation = CardDefaults.cardElevation(8.dp)
                         , colors = CardDefaults.cardColors(containerColor = Color(0xFF09131f)), shape = RoundedCornerShape(16.dp)) {
                         Row(modifier = Modifier.width(376.dp).height(150.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
                             Spacer(Modifier.height(75.dp))
                             Text(text = "🌱", fontSize = 14.sp, color = Color.White)
                             Text(text = "AQI ${aqi?.usAqi?:"unknown"}", fontSize = 14.sp, color = Color.White)
                             Text(text = getAqiLevel(aqi?.usAqi?:0), fontSize = 14.sp, color = Color.White)
                             Box(Modifier.size(12.dp).background(color = getAqiColor(aqi?.usAqi), shape = CircleShape))



                         }
                     }}
             }

        }
    }

}
@Composable
fun HourWeather(hourlyWeather: HourlyWeather?){
    if (hourlyWeather==null || hourlyWeather.time.isEmpty()) return
    LazyRow(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Absolute.Left) {


        val hourShow=hourlyWeather.time.take(24)
        items(hourShow.size){
            val hour=hourShow[it].toPersianTime()
            val weatherCode =hourlyWeather.weather_code.getOrNull(it)?:0
            val temp=hourlyWeather.temperature_2m.getOrNull(it)?:0

            Card(shape =RoundedCornerShape(30.dp), modifier = Modifier.height(120.dp).width(80.dp).padding(horizontal = 14.dp)
                , elevation = CardDefaults.cardElevation(8.dp)
                , colors = CardDefaults.cardColors(containerColor = Color(0xFF182040)) ){
                Column(modifier = Modifier.height(120.dp).width(80.dp)
                    ,horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(text =hour, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(text = getWeatherEmoji(weatherCode), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White)
                    Spacer(Modifier.height(6.dp))
                    Text(text = "${temp}°", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 12.sp)
                }}
        }
    }
}