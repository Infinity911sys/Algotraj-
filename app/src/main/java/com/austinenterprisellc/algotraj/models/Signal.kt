package com.austinenterprisellc.algotraj.models

data class Signal(
    val t: String,
    val type: String,
    val payload: Map<String, Any?>,
    val confidence: Double
)
