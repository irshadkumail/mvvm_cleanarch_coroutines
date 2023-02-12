package com.example.demo.domain.models

data class AverageLatencyModel(
    val domain: String,
    val latency: Long,
    val isAvailable: Boolean
)