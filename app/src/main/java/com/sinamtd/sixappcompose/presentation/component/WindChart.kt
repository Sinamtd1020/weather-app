package com.sinamtd.sixappcompose.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun WeeklyWindSpeedChart(
    windSpeed: List<Double>,
    dates: List<String>,
    modifier: Modifier = Modifier
){

    if (windSpeed.isEmpty()) return

    val daysOfWeek=dates.map { it.toDayOfWeekTemp() }

    val windSpeedPoints = windSpeed.mapIndexed { index, value ->
        Point(index.toFloat() , value.toFloat())
    }

    val windSpeedLine = Line(
        dataPoints = windSpeedPoints,
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

    val xAxis = AxisData.Builder()
        .steps(windSpeedPoints.size - 1)
        .axisStepSize(50.dp)

        .axisPosition(Gravity.BOTTOM)
        .axisLabelColor(Color.Black)
        .axisLineColor(Color.Black)
        .axisLabelFontSize(12.sp)
        .labelData { index ->
            daysOfWeek.getOrNull(index) ?: ""
        }
        .build()


    val yAxis = AxisData.Builder()
        .steps(10)
        .axisPosition(Gravity.LEFT)
        .axisLabelColor(Color.Black)
        .axisLineColor(Color.Black)
        .axisLabelFontSize(12.sp)
        .labelData { index ->
            val min = windSpeed.minOrNull() ?: 0.0
            val max = windSpeed.maxOrNull() ?: 200.0
            val value = min + (max - min) * index / 10
            "${value.toInt()} km/h"
        }
        .build()

    val chartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(windSpeedLine)
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
            .width(140.dp)
            .height(240.dp),
        lineChartData = chartData
    )

}