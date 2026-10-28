package com.sinamtd.sixappcompose.presentation.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.Gravity
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.sinamtd.sixappcompose.utils.toDayOfWeekTemp


@Composable
fun WeeklyTemperatureChart(
    maxTemperatures: List<Double>,
    minTemperatures: List<Double>,
    dates: List<String>,
    modifier: Modifier = Modifier
) {
    // اگر داده‌ها خالی بودند، چیزی نمایش نده
    if (maxTemperatures.isEmpty() || minTemperatures.isEmpty()) return

    // نام روزهای هفته
    val daysOfWeek=dates.map { it.toDayOfWeekTemp() }


    // تبدیل داده‌ها به نقاط
    val maxPoints = maxTemperatures.mapIndexed { index, value ->
        Point(index.toFloat() , value.toFloat())
    }
    val minPoints = minTemperatures.mapIndexed { index, value ->
        Point(index.toFloat(), value.toFloat())
    }

    // ====== خط بیشینه (قرمز-نارنجی) ======
    val maxLine = Line(
        dataPoints = maxPoints,
        lineStyle = LineStyle(
            color = Color(0xFFFF5722),
            width = 3f,
            lineType = LineType.Straight()
        ),
        selectionHighlightPoint = SelectionHighlightPoint(
            color = Color(0xFFFF5722),
            radius = 6.dp
        ),
        shadowUnderLine = ShadowUnderLine(
            color = Color(0xFFFF5722).copy(alpha = 0.2f)
        )
    )

    // ====== خط کمینه (آبی روشن) ======
    val minLine = Line(
        dataPoints = minPoints,
        lineStyle = LineStyle(
            color = Color(0xFF4FC3F7),
            width = 3f,
            lineType = LineType.Straight()
        ),
        selectionHighlightPoint = SelectionHighlightPoint(
            color = Color(0xFF4FC3F7),
            radius = 6.dp
        ),
        shadowUnderLine = ShadowUnderLine(
            color = Color(0xFF4FC3F7).copy(alpha = 0.2f)
        )
    )

    // ====== محور X (روزهای هفته) ======
    val xAxis = AxisData.Builder()
        .steps(maxPoints.size - 1)
        .axisStepSize(50.dp)

        .axisPosition(Gravity.BOTTOM)
        .axisLabelColor(Color.Black)
        .axisLineColor(Color.Black)
        .axisLabelFontSize(12.sp)
        .labelData { index ->
            daysOfWeek.getOrNull(index) ?: ""
        }
        .build()

    // ====== محور Y (دما) ======
    val allTemps = maxTemperatures + minTemperatures
    val minTemp = allTemps.minOrNull() ?: -40.0
    val maxTemp = allTemps.maxOrNull() ?: 40.0
    val step = (maxTemp - minTemp) / 12

    val yAxis = AxisData.Builder()
        .steps(12)
        .axisPosition(Gravity.LEFT)

        .axisLabelColor(Color.Black)
        .axisLineColor(Color.Black)
        .axisLabelFontSize(12.sp)
        .labelData { index ->
            val value = minTemp + step * index
            "${value.toInt()}°"
        }

        .build()

    // ====== داده‌های کامل نمودار ======
    val chartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(maxLine, minLine)
        ),
        xAxisData = xAxis,
        yAxisData = yAxis,
        gridLines = GridLines(
            color = Color.White.copy(alpha = 0.15f),
            enableHorizontalLines = true,
            enableVerticalLines = true
        ),
        backgroundColor = Color.Transparent
    )
    LineChart(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp),
        lineChartData = chartData
    )
}