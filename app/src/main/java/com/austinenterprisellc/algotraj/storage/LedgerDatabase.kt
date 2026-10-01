package com.austinenterprisellc.algotraj.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LedgerEntryEntity::class], version = 1, exportSchema = false)
abstract class LedgerDatabase : RoomDatabase() {
    abstract fun ledgerDao(): LedgerDao
}
