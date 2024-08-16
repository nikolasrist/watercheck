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

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Navigation buttons
            Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { selectedTimeRange = LAST_12_HOURS }) {
                    Text("Last 12 Hours")
                }
                Button(onClick = { selectedTimeRange = LAST_7_DAYS }) {
                    Text("Last 7 Days")
                }
                Button(onClick = { selectedTimeRange = 24 }) {
                    Text("All")
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
            ThroughputPlot(filteredThroughputs)
        }

    }
}