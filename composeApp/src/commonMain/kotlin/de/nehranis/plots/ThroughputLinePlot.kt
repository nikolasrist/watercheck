package de.nehranis.plots

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import de.nehranis.Throughput
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.Symbol
import io.github.koalaplot.core.legend.FlowLegend
import io.github.koalaplot.core.legend.LegendLocation
import io.github.koalaplot.core.line.LinePlot
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.util.VerticalRotation
import io.github.koalaplot.core.util.generateHueColorPalette
import io.github.koalaplot.core.util.rotateVertically
import io.github.koalaplot.core.xygraph.CategoryAxisModel
import io.github.koalaplot.core.xygraph.DefaultPoint
import io.github.koalaplot.core.xygraph.FloatLinearAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.XYGraphScope
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormatBuilder
import kotlin.math.ceil

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun ThroughputPlot(
    throughputs: List<Throughput>,
    title: String = "Durchschnitt Durchfluss in den letzten ${throughputs.count()} Stunden.",
    paddingMod: Modifier = Modifier.padding(16.dp)
) {
    // Extract meanValue and timestamp (hour) from each Throughput object
    val meanValues = throughputs.map { it.meanValue }
    val hours = throughputs.map { it.timestamp.hour.toString().padStart(2, '0') }

    // Calculate the Y-axis range based on the max value
    val maxMeanValue = meanValues.maxOrNull() ?: 0.0
    val yAxisMax = (ceil(maxMeanValue / 50.0) * 50.0).toFloat()

    // Create the chart layout
    ChartLayout(
        modifier = paddingMod,
        title = { ChartTitle(title) },
    ) {
        XYGraph(
            xAxisModel = CategoryAxisModel(hours),
            yAxisModel = FloatLinearAxisModel(
                0f..yAxisMax,
                minimumMajorTickSpacing = 50.dp,
            ),
            xAxisLabels = {
                AxisLabel(it, Modifier.padding(top = 2.dp))
            },
            xAxisTitle = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AxisTitle("Uhrzeit")
                }
            },
            yAxisLabels = {
                AxisLabel(it.toString(), Modifier.absolutePadding(right = 2.dp))
            },
            yAxisTitle = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.TopStart
                ) {
                    AxisTitle(
                        "Durchschnittswert",
                        modifier = Modifier.rotateVertically(VerticalRotation.COUNTER_CLOCKWISE)
                            .padding(bottom = 16.dp)
                    )
                }
            }
        ) {
            // Plotting the line for the throughput mean values
            chart(
                data = meanValues.mapIndexed { index, meanValue ->
                    DefaultPoint(hours[index], meanValue.toFloat())
                }
            )
        }
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
private fun XYGraphScope<String, Float>.chart(
    data: List<DefaultPoint<String, Float>>,
) {
    LinePlot(
        data = data,
        lineStyle = LineStyle(
            brush = SolidColor(Color.Black),
            strokeWidth = 2.dp
        ),
        symbol = { point ->
            Symbol(
                shape = CircleShape,
                fillBrush = SolidColor(Color.Black),
                modifier = Modifier
            )
        }
    )
}