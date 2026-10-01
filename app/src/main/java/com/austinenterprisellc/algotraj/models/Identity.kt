package com.austinenterprisellc.algotraj.models

data class Identity(
    val id: String,
    val entityId: String,
    val attributes: Map<String, String> = emptyMap()
)
