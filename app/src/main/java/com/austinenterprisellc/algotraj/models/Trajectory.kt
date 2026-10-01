package com.austinenterprisellc.algotraj.models

data class TrajectorySegment(
    val seq: Int,
    val fromState: Map<String, Any?>,
    val toState: Map<String, Any?>,
    val confidence: Double,
    val deviationRisk: Double,
    val notes: String? = null
)

data class Trajectory(
    val id: String,
    val entityId: String,
    val generatedAt: String,
    val segments: List<TrajectorySegment>,
    val globalConfidence: Double,
    val globalDeviationRisk: Double,
    val flags: List<String>
)
