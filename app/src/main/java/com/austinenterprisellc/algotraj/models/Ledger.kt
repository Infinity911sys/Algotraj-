package com.austinenterprisellc.algotraj.models

data class LedgerEntry(
    val id: String,
    val entityId: String,
    val t: String,
    val type: String,
    val payloadJson: String,
    val correlationId: String? = null
)
