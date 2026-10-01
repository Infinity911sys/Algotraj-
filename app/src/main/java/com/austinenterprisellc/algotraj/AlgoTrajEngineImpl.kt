package com.austinenterprisellc.algotraj

import com.austinenterprisellc.algotraj.models.Signal
import com.austinenterprisellc.algotraj.models.Trajectory
import com.austinenterprisellc.algotraj.models.TrajectorySegment
import com.austinenterprisellc.algotraj.util.newId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

interface AlgoTrajEngine {
    suspend fun ingestSignal(entityId: String, signal: Signal)
    suspend fun generateTrajectory(entityId: String): Trajectory
}

class AlgoTrajEngineImpl : AlgoTrajEngine {
    private val signalBuffer = mutableMapOf<String, MutableList<Signal>>()
    private val mutex = Mutex()

    override suspend fun ingestSignal(entityId: String, signal: Signal) {
        require(entityId.isNotBlank()) { "entityId must not be blank" }
        require(signal.confidence.isFinite() && signal.confidence in 0.0..1.0) {
            "confidence must be between 0.0 and 1.0"
        }
        mutex.withLock {
            signalBuffer.getOrPut(entityId) { mutableListOf() }.add(signal)
        }
    }

    override suspend fun generateTrajectory(entityId: String): Trajectory {
        val signals = mutex.withLock { signalBuffer[entityId].orEmpty().toList() }
        return withContext(Dispatchers.Default) {
            val segments = signals.mapIndexed { index, signal ->
                TrajectorySegment(
                    seq = index + 1,
                    fromState = mapOf("prevIndex" to index),
                    toState = mapOf("signalType" to signal.type, "payload" to signal.payload),
                    confidence = signal.confidence,
                    deviationRisk = 1.0 - signal.confidence,
                    notes = "Generated from signal at ${signal.t}"
                )
            }

            val globalConfidence = segments.map { it.confidence }.averageOrZero()
            val globalDeviationRisk = segments.map { it.deviationRisk }.averageOrZero()
            val now = System.currentTimeMillis().toString()

            Trajectory(
                id = "$entityId-${newId()}",
                entityId = entityId,
                generatedAt = now,
                segments = segments,
                globalConfidence = globalConfidence,
                globalDeviationRisk = globalDeviationRisk,
                flags = emptyList()
            )
        }
    }

    private fun List<Double>.averageOrZero(): Double = if (isEmpty()) 0.0 else average()
}
