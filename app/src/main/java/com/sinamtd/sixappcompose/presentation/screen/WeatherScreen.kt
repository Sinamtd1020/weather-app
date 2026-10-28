package com.sinamtd.sixappcompose.presentation.screen



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.sinamtd.sixappcompose.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sinamtd.sixappcompose.data.remote.dto.DailyDto
import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResult
import com.sinamtd.sixappcompose.presentation.components.ErrorWithRetry
import com.sinamtd.sixappcompose.presentation.navigation.Screens
import com.sinamtd.sixappcompose.presentation.viewmodel.WeatherViewModel
import com.sinamtd.sixappcompose.utils.getImage
import com.sinamtd.sixappcompose.utils.getWeatherDescription
import com.sinamtd.sixappcompose.utils.getWeatherEmoji
import com.sinamtd.sixappcompose.utils.toDayOfWeek

@Composable
fun WeatherScreen(viewmodel: WeatherViewModel= hiltViewModel(),navController: NavController){
    val state by viewmodel.state.collectAsState()




    when{
        state.isLoading->{
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A1628)), contentAlignment = Alignment.Center){
            CircularProgressIndicator(color = Color.White)}
        }
        state.error.isNotEmpty()->{
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A1628)), contentAlignment = Alignment.Center){
                ErrorWithRetry(errorMessage = state.error, onRetry = {
                    val lastCity=state.selectedCity?:"Tehran"
                    viewmodel.getWeather(lastCity)
                })
            }
        }
        state.weather!=null -> {
            val weather=state.weather
            val current=weather?.current
            val daily=weather?.daily
            Box() {
                Box(modifier = Modifier.fillMaxSize().statusBarsPadding(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(getImage(current?.weather_code,current?.is_day)),
                        "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SearchBar(
                            query = state.searchQuery,
                            onQueryChange = viewmodel::updateSearchQuery,
                            onClear = viewmodel::clearSearch,
                            isSearching = state.isSearching
                        )
                        if (state.searchResult.isNotEmpty()) {
                            SearchResultsList(
                                results = state.searchResult,
                                onCitySelected = viewmodel::selectCity
                            )
                        }
                        else {
                            Text(
                                state.selectedCity ?: "Tehran",
                                fontSize = 25.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 15.dp)
                            )
                            Spacer(Modifier.height(140.dp))
                            Text(
                                text = "${current?.temperature_2m?.toInt() ?: "- -"}° ",
                                color = Color.White,
                                fontSize = 70.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(5.dp))
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Text(
                                    text = getWeatherDescription(current?.weather_code),
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    text = getWeatherEmoji(code = current?.weather_code?:0),
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                               }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "Humidity : ${current?.relative_humidity_2m}",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "Fell like : ${current?.apparent_temperature} ",
                                fontSize = 14.sp,
                                color = Color.White
                            )


                        }
                    }
                    if (state.searchResult.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        Column(
                            modifier = Modifier.fillMaxWidth().height(260.dp).navigationBarsPadding()
                                .background(
                                    color = Color(0x990B2149),
                                    shape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp)
                                )
                        ) {
                            Spacer(Modifier.height(20.dp))

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Absolute.Left,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.width(15.dp))
                                Icon(
                                    Icons.Outlined.CalendarToday,
                                    "",
                                    tint = Color.White,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(Modifier.width(5.dp))
                                Text(
                                    "weekly Forecast",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(Modifier.height(15.dp))
                            if (daily != null && daily.time.isNotEmpty()) WeekWeather(daily)
                            Spacer(Modifier.height(25.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextButton(
                                    onClick = {
                                        navController.navigate(Screens.Details.rout) {
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
                                { Text("more Details", fontSize = 12.sp, color = Color.White) }
                                Spacer(Modifier.width(12.dp))
                            }

                        }
                    }}

                }
            }
        }
    }


}

@Composable
fun WeekWeather(daily: DailyDto?){
    if (daily==null || daily.time.isEmpty()) return
    LazyRow(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Absolute.Left) {


           val dayShow=daily.time.takeLast(6)
           items(dayShow.size){
           val date=dayShow[it].toDayOfWeek()
           val weatherCode =daily.weather_code.getOrNull(it)?:0
           val temp=daily.temperature_2m_max.getOrNull(it)?.toInt()?:0


                Card(shape =RoundedCornerShape(30.dp), modifier = Modifier.height(120.dp).width(80.dp).padding(horizontal = 14.dp)
               , elevation = CardDefaults.cardElevation(8.dp)
                    , colors = CardDefaults.cardColors(containerColor = Color(0xCC0B2149)) ){
                    Column(modifier = Modifier.height(120.dp).width(75.dp)
                        ,horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
               Text(text =date, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 12.sp)
               Spacer(Modifier.height(6.dp))
               Text(text = getWeatherEmoji(weatherCode), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White)
               Spacer(Modifier.height(6.dp))
               Text(text = "${temp}°", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 12.sp)
           }}
       }
    }
}

@Composable
fun SearchResultsList(
    results: List<GeocodingResult>,
    onCitySelected: (GeocodingResult) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .background(Color(0xCC1C2F3F), RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        items(results) { city ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCitySelected(city) }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = city.name, // نام انگلیسی
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text =  city.country ?:"unKnown", // کشور
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "📍",
                    color = Color.White
                )
            }
            Divider(
                color = Color.White.copy(alpha = 0.1f),
                thickness = 0.5.dp
            )
        }
    }
}


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    isSearching: Boolean
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        placeholder = {
            Text(
                text = "Enter city name in english ...  ",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(textAlign = TextAlign.Left)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0x991C2F3F), // پس‌زمینه شیشه‌ای
            unfocusedContainerColor = Color(0x991C2F3F),
            focusedIndicatorColor = Color.Transparent, // خط زیرین را حذف می‌کند
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(16.dp), // گوشه‌های گرد
       leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "جستجو",
                tint = Color.White
            )
        },
        trailingIcon = {
            when {
                isSearching -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
                query.isNotEmpty() -> {
                    IconButton(
                        onClick = onClear,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "پاک کردن",
                            tint = Color.White
                        )
                    }
                }
            }
        },
        singleLine = true,
        textStyle = androidx.compose.ui.text.TextStyle(
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Left
        )
    )
}