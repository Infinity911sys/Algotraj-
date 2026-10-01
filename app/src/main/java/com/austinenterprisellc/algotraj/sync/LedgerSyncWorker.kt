package com.austinenterprisellc.algotraj.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LedgerSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return Result.failure()
    }
}
