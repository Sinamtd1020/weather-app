package com.sinamtd.sixappcompose.presentation.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sinamtd.sixappcompose.presentation.component.HourlyHumidityChart
import com.sinamtd.sixappcompose.presentation.component.HourlyTemperatureChart
import com.sinamtd.sixappcompose.presentation.component.HourlyUvChart
import com.sinamtd.sixappcompose.presentation.component.WeeklyPrecipitationChart
import com.sinamtd.sixappcompose.presentation.component.WeeklyTemperatureChart
import com.sinamtd.sixappcompose.presentation.component.WeeklyWindSpeedChart
import com.sinamtd.sixappcompose.presentation.navigation.Screens
import com.sinamtd.sixappcompose.presentation.viewmodel.WeatherViewModel
import com.sinamtd.sixappcompose.utils.charNameDay
import com.sinamtd.sixappcompose.utils.charNameWeek

@Composable
fun ChartScreen(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1628))
            .systemBarsPadding()
    ) {
        when {
            // ====== حالت بارگذاری ======
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            // ====== حالت خطا ======
            state.error.isNotEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "❌ ${state.error}",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            // ====== حالت نمایش داده‌ها ======
            state.weather != null -> {
                val weather = state.weather
                val hourly = weather?.hourly
                val daily = weather?.daily

                // آماده‌سازی داده‌ها
                val hourlyTemps = hourly?.temperature_2m?.take(24) ?: emptyList()
                val hourlyUv = hourly?.uv_index?.take(24) ?: emptyList()
                val hourlyHumidity = hourly?.relative_humidity_2m?.take(24) ?: emptyList()
                val dailyMax = daily?.temperature_2m_max?.take(7) ?: emptyList()
                val dailyMin = daily?.temperature_2m_min?.take(7) ?: emptyList()
                val dailyWindSpeed = daily?.wind_speed_10m_max?.take(7) ?: emptyList()
                val dailyPrecipitation = daily?.precipitation_sum?.take(7) ?: emptyList()
                val date=daily?.time?:emptyList()

                val weeklyList=listOf("Temps","WindSpeed","Precipitation")
                var weeklySelect by remember { mutableStateOf(0) }

                val dailyList=listOf("Temps","Uv","Humidity")
                var dailySelect by remember { mutableStateOf(0) }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart){
                    IconButton(onClick = {navController.navigate(Screens.Details.rout) {
                        popUpTo(
                            Screens.Details.rout
                        ) { inclusive = true }
                    }})
                    { Icon(Icons.Filled.ArrowBack,"", modifier = Modifier.size(35.dp), tint = Color.White) }}
                    // ====== عنوان صفحه ======
                    Text(
                        text = " Temperature charts 📊",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        textAlign = TextAlign.Left
                    )

                    // ====== نام شهر ======
                    Text(
                        text = " ${state.selectedCity ?: "tehran"} 📍",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        textAlign = TextAlign.Left
                    )

                    // ====== نمودار ۲۴ ساعت ======
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xCC1C2F3F)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                                dailyList.forEachIndexed { index, string ->
                                    RadioButton(selected = index==dailySelect, onClick = {dailySelect=index}, colors = RadioButtonDefaults.colors(selectedColor = Color.White, unselectedColor = Color.Gray))
                                    Text(text = string, fontSize = 10.sp, color = Color.White)
                                }
                            }

                            Text(
                                text = charNameDay(dailySelect),
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Left
                            )

                            Spacer(modifier = Modifier.height(12.dp))


                            when(dailySelect){
                                0->HourlyTemperatureChart(
                                    temperatures = hourlyTemps,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                1-> HourlyUvChart(hourlyUv, Modifier.fillMaxWidth())
                                2-> HourlyHumidityChart(hourlyHumidity, Modifier.fillMaxWidth())
                            }
                        }
                    }

                    // ====== نمودار ۷ روز ======
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xCC1C2F3F)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                                weeklyList.forEachIndexed { index, string ->
                                    RadioButton(selected = index==weeklySelect, onClick = {weeklySelect=index}, colors = RadioButtonDefaults.colors(selectedColor = Color.White, unselectedColor = Color.Gray))
                                    Text(text = string, fontSize = 10.sp, color = Color.White)
                                }
                            }
                            Text(
                                text = charNameWeek(weeklySelect),
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))


                            // ====== افسانه (Legend) ======
                            if ( weeklySelect==0 ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                LegendItem(color = Color(0xFFFF5722), label = "Max")
                                Spacer(modifier = Modifier.width(16.dp))
                                LegendItem(color = Color(0xFF4FC3F7), label = "Min")
                            }}
                            when(weeklySelect){
                                0->WeeklyTemperatureChart(
                                    maxTemperatures = dailyMax,
                                    minTemperatures = dailyMin,
                                    dates =date ,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                1-> WeeklyWindSpeedChart(dailyWindSpeed,date, Modifier.fillMaxWidth())
                                2-> WeeklyPrecipitationChart(dailyPrecipitation,date, Modifier.fillMaxWidth())
                            }


                        }
                    }

                    // ====== اطلاعات اضافی ======
                    val current = weather?.current
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xCC1C2F3F)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier

.fillMaxWidth()
.padding(16.dp),
horizontalArrangement = Arrangement.SpaceEvenly
) {
    InfoItem(
        label = "humidity 💧",
        value = "${current?.relative_humidity_2m ?: 0}%"
    )
    InfoItem(
        label = " wind speed 💨",
        value = " km/h ${current?.wind_speed_10m ?: 0}"
    )
    InfoItem(
        label = " pressure 🌡️",
        value = "hPa ${current?.surface_pressure?.toInt() ?: 0} "
    )
}
}

Spacer(modifier = Modifier.height(16.dp))
}
}
}
}
}

// ====== کامپوننت افسانه (راهنما) ======
@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
    }
}

// ====== کامپوننت آیتم اطلاعاتی ======
@Composable
fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 11.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}