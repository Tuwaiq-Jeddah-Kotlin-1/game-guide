package com.example.gameguide.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class Worker (private val context: Context, val params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result{
        NotificationPointer(context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString())
        return Result.success()

    }
}