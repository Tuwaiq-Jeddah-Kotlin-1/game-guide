package com.example.gameguide.notification

import android.content.Context
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters

class Worker(private val context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            NotificationPointer(context).createNotification(
                inputData.getString("title").toString(),
                inputData.getString("message").toString()
            )
        }
        return Result.success()

    }
}