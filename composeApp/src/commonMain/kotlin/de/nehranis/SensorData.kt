package de.nehranis

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Throughput(
    val id: Int,
    val minValue: Double,
    val maxValue: Double,
    val meanValue: Double,
    val timestamp: LocalDateTime
)