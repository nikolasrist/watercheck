package de.nehranis

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.nehranis.plots.ThroughputPlot
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import kotlinx.datetime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

const val LAST_12_HOURS = 12
const val LAST_7_DAYS = 7
const val LAST_30_DAYS = 30
const val HOURS_TITLE = "Stunden"
const val DAYS_TITLE = "Tage"

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val throughputs = listOf(
            Throughput(
                1,
                1.0,
                50.0,
                10.0,
                Clock.System.now().minus(12, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                2,
                0.0,
                0.0,
                15.0,
                Clock.System.now().minus(11, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                3,
                0.0,
                0.0,
                20.0,
                Clock.System.now().minus(10, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                4,
                0.0,
                0.0,
                25.0,
                Clock.System.now().minus(9, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                5,
                0.0,
                0.0,
                30.0,
                Clock.System.now().minus(8, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                6,
                0.0,
                0.0,
                18.0,
                Clock.System.now().minus(7, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                7,
                0.0,
                0.0,
                22.0,
                Clock.System.now().minus(6, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                8,
                0.0,
                0.0,
                27.0,
                Clock.System.now().minus(5, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                9,
                0.0,
                0.0,
                19.0,
                Clock.System.now().minus(4, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                10,
                0.0,
                0.0,
                24.0,
                Clock.System.now().minus(3, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                11,
                0.0,
                0.0,
                29.0,
                Clock.System.now().minus(2, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Throughput(
                12,
                0.0,
                0.0,
                33.0,
                Clock.System.now().minus(1, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )

        var selectedTimeRange by remember { mutableStateOf(LAST_12_HOURS) }
        var selectedTimeRangeTitle: String by remember { mutableStateOf("Stunden") }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Navigation buttons
            FlowRow(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    selectedTimeRange = LAST_12_HOURS
                    selectedTimeRangeTitle = HOURS_TITLE
                }) {
                    Text("Letzten 12 Stunden")
                }
                Button(onClick = {
                    selectedTimeRange = LAST_7_DAYS
                    selectedTimeRangeTitle = DAYS_TITLE
                }) {
                    Text("Letzten 7 Tage")
                }
                Button(onClick = {
                    selectedTimeRange = LAST_30_DAYS
                    selectedTimeRangeTitle = DAYS_TITLE
                }) {
                    Text("Letzten 30 Tage")
                }
            }

            // Filter the throughput data based on the selected time range
            val filteredThroughputs = when (selectedTimeRange) {
                LAST_12_HOURS -> throughputs.filter { it.id < 7 }

                LAST_7_DAYS -> throughputs.filter { it.id > 6 }

                else -> {
                    throughputs
                }
            }
            // Display the plot based on the filtered data
            ThroughputPlot(
                filteredThroughputs,
                title = "Durchschnitt Durchfluss in den letzten ${selectedTimeRange} ${selectedTimeRangeTitle}.",
                xAxisTitle = selectedTimeRangeTitle
            )
        }

    }
}