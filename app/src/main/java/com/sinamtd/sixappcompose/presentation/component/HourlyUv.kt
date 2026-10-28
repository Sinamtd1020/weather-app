package com.sinamtd.sixappcompose.presentation.component

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


@Composable
fun HourlyUvChart(
    uv: List<Double>,
    modifier: Modifier = Modifier
) {

    if (uv.isEmpty()) return

    val points = uv.mapIndexed { index, value ->
        Point(index.toFloat(), value.toFloat())
    }

    val line = Line(
        dataPoints = points,
        lineStyle = LineStyle(
            color = Color(0xFF4FC3F7),
            width = 3f,
            lineType = LineType.Straight()
        ),
        selectionHighlightPoint = SelectionHighlightPoint(),
        shadowUnderLine = ShadowUnderLine()
    )

    val xAxis = AxisData.Builder()
        .steps(points.size - 1)
        .axisPosition(Gravity.BOTTOM)
        .axisLabelColor(Color.Black)
        .axisLineColor(Color.Black)
        .axisLabelFontSize(12.sp)
        .labelData { index ->
            when (index) {
                4 -> "04:00"
                6 -> "06:00"
                8 -> "08:00"
                10 -> "10:00"
                12 -> "12:00"
                14-> "14:00"
                16 -> "16:00"
                18 -> "18:00"
                20-> "20:00"
                else -> ""
            }
        }
        .build()

    val seenValue=mutableSetOf<Double>()

    val yAxis = AxisData.Builder()
        .steps(5)
        .axisPosition(Gravity.LEFT)
        .axisLabelColor(Color.Black)
        .axisLineColor(Color.Black)
        .axisLabelFontSize(12.sp)
        .labelData { index ->
            val min = uv.minOrNull() ?: 0.0
            val max = uv.maxOrNull() ?: 15.0
            val value = min + (max - min) * index / 5
            "${value.toInt()}"
        }
        .build()

    val chartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(line)
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