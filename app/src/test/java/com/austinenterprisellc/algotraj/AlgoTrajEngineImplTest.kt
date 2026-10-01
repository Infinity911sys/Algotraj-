package com.austinenterprisellc.algotraj

import com.austinenterprisellc.algotraj.models.Signal
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AlgoTrajEngineImplTest {
    @Test
    fun generateTrajectoryUsesIngestedSignalsInOrder() = runBlocking {
        val engine = AlgoTrajEngineImpl()
        engine.ingestSignal("device", Signal("1", "first", emptyMap(), 0.8))
        engine.ingestSignal("device", Signal("2", "second", emptyMap(), 0.6))

        val trajectory = engine.generateTrajectory("device")

        assertEquals(listOf(1, 2), trajectory.segments.map { it.seq })
        assertEquals(listOf("first", "second"), trajectory.segments.map { it.toState["signalType"] })
        assertEquals(0.7, trajectory.globalConfidence, 0.0001)
        assertEquals(0.3, trajectory.globalDeviationRisk, 0.0001)
    }

    @Test
    fun generateTrajectoryForUnknownEntityIsEmpty() = runBlocking {
        val trajectory = AlgoTrajEngineImpl().generateTrajectory("missing")

        assertEquals(emptyList<Int>(), trajectory.segments.map { it.seq })
        assertEquals(0.0, trajectory.globalConfidence, 0.0)
        assertEquals(0.0, trajectory.globalDeviationRisk, 0.0)
    }

    @Test
    fun ingestRejectsConfidenceOutsideRange() = runBlocking {
        val engine = AlgoTrajEngineImpl()

        try {
            engine.ingestSignal("device", Signal("1", "invalid", emptyMap(), 1.1))
            throw AssertionError("Expected invalid confidence to be rejected")
        } catch (_: IllegalArgumentException) {
        }
    }
}
