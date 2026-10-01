package com.austinenterprisellc.algotraj.models

data class GovernancePolicy(
    val minimumConfidence: Double = 0.0,
    val maximumDeviationRisk: Double = 1.0
) {
    init {
        require(minimumConfidence in 0.0..1.0)
        require(maximumDeviationRisk in 0.0..1.0)
    }

    fun evaluate(trajectory: Trajectory): List<String> = buildList {
        if (trajectory.globalConfidence < minimumConfidence) {
            add("confidence_below_policy")
        }
        if (trajectory.globalDeviationRisk > maximumDeviationRisk) {
            add("deviation_risk_above_policy")
        }
    }
}
