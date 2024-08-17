package de.nehranis

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.nehranis.plots.ThroughputPlot
import de.nehranis.plots.TimeRange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

const val LAST_12_HOURS = 12
const val LAST_7_DAYS = 7
const val LAST_14_DAYS = 14
const val LAST_30_DAYS = 30
const val HOURS_TITLE = "Stunden"
const val DAYS_TITLE = "Tage"
val sensorApi = SensorAPI()

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var selectedTimeRange by remember { mutableStateOf(TimeRange.HOURS) }
        var selectedTimeRangeValue by remember { mutableStateOf(LAST_12_HOURS) }
        var selectedTimeRangeTitle by remember { mutableStateOf("Stunden") }
        var filteredThroughputs by remember { mutableStateOf<List<Throughput>?>(null) }
        var isLoading by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()

        // Initial data load using LaunchedEffect
        LaunchedEffect(selectedTimeRangeValue) {
            loadData(coroutineScope, selectedTimeRangeValue) { data, loading ->
                filteredThroughputs = data
                isLoading = loading
            }
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Navigation buttons
            FlowRow(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    selectedTimeRangeValue = LAST_12_HOURS
                    selectedTimeRangeTitle = HOURS_TITLE
                    selectedTimeRange = TimeRange.HOURS
                    loadData(coroutineScope, selectedTimeRangeValue) { data, loading ->
                        filteredThroughputs = data
                        isLoading = loading
                    }
                }) {
                    Text("Letzten 12 Stunden")
                }
                Button(onClick = {
                    selectedTimeRangeValue = LAST_7_DAYS
                    selectedTimeRangeTitle = DAYS_TITLE
                    selectedTimeRange = TimeRange.DAYS
                    loadData(coroutineScope, selectedTimeRangeValue) { data, loading ->
                        filteredThroughputs = data
                        isLoading = loading
                    }
                }) {
                    Text("Letzten 7 Tage")
                }
                Button(onClick = {
                    selectedTimeRangeValue = LAST_14_DAYS
                    selectedTimeRangeTitle = DAYS_TITLE
                    selectedTimeRange = TimeRange.DAYS
                    loadData(coroutineScope, selectedTimeRangeValue) { data, loading ->
                        filteredThroughputs = data
                        isLoading = loading
                    }
                }) {
                    Text("Letzten 14 Tage")
                }
                Button(onClick = {
                    selectedTimeRangeValue = LAST_30_DAYS
                    selectedTimeRangeTitle = DAYS_TITLE
                    selectedTimeRange = TimeRange.DAYS
                    loadData(coroutineScope, selectedTimeRangeValue) { data, loading ->
                        filteredThroughputs = data
                        isLoading = loading
                    }
                }) {
                    Text("Letzten 30 Tage")
                }
            }

            // Show a loading indicator while data is being fetched
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                // Display the plot based on the filtered data
                filteredThroughputs?.let {
                    ThroughputPlot(
                        it,
                        title = "Durchschnitt Durchfluss in den letzten $selectedTimeRangeValue $selectedTimeRangeTitle.",
                        xAxisTitle = selectedTimeRangeTitle,
                        timeRange = selectedTimeRange
                    )
                }
            }
        }
    }
}

private fun loadData(
    scope: CoroutineScope,
    timeRange: Int,
    onResult: (List<Throughput>?, Boolean) -> Unit
) {
    scope.launch {
        onResult(null, true)  // Set loading state to true
        val data = fetchData(timeRange)  // Fetch data from API
        onResult(data, false)  // Set data and loading state to false
    }
}

private suspend fun fetchData(selectedTimeRange: Int): List<Throughput> {
    return when (selectedTimeRange) {
        LAST_12_HOURS -> sensorApi.getHourlyThroughputs(LAST_12_HOURS).reversed()
        LAST_7_DAYS -> sensorApi.getDailyThroughputs(LAST_7_DAYS).reversed()
        LAST_14_DAYS -> sensorApi.getDailyThroughputs(LAST_14_DAYS).reversed()
        LAST_30_DAYS -> sensorApi.getDailyThroughputs(LAST_30_DAYS).reversed()
        else -> {
            throw NotImplementedError()
        }
    }
}