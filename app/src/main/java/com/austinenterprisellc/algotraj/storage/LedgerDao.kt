package com.austinenterprisellc.algotraj.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LedgerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entry: LedgerEntryEntity)

    @Query("SELECT * FROM ledger_entries WHERE entityId = :entityId ORDER BY t, id")
    fun observeForEntity(entityId: String): Flow<List<LedgerEntryEntity>>

    @Query("SELECT * FROM ledger_entries ORDER BY t, id")
    suspend fun getAll(): List<LedgerEntryEntity>
}
