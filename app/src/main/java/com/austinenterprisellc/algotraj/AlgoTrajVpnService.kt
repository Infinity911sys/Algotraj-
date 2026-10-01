package com.austinenterprisellc.algotraj

import android.content.Intent
import android.net.VpnService
import android.os.IBinder

class AlgoTrajVpnService : VpnService() {
    override fun onBind(intent: Intent?): IBinder? = super.onBind(intent)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        stopSelfResult(startId)
        return START_NOT_STICKY
    }
}
