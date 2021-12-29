package com.example.gameguide.notification

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.gameguide.MainActivity
import java.util.concurrent.TimeUnit

class NotificationRepo() {
    private val list = listOf("hello gamers check us out!!", "check latest games details").random()

    fun myNotification(mainActivity: MainActivity){
        val myWorkRequest = PeriodicWorkRequest.Builder(Worker::class.java, 48, TimeUnit.HOURS)
            .setInputData(workDataOf("title" to "Game Guide", "message" to list))
            .build()
        WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
            "periodicStockWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            myWorkRequest
        )
    }
}