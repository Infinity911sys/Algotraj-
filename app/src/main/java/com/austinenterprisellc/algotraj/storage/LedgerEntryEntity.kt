package com.austinenterprisellc.algotraj.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.austinenterprisellc.algotraj.models.LedgerEntry

@Entity(tableName = "ledger_entries")
data class LedgerEntryEntity(
    @PrimaryKey val id: String,
    val entityId: String,
    val t: String,
    val type: String,
    val payloadJson: String,
    val correlationId: String?
) {
    fun toModel() = LedgerEntry(id, entityId, t, type, payloadJson, correlationId)

    companion object {
        fun from(entry: LedgerEntry) = LedgerEntryEntity(
            id = entry.id,
            entityId = entry.entityId,
            t = entry.t,
            type = entry.type,
            payloadJson = entry.payloadJson,
            correlationId = entry.correlationId
        )
    }
}
