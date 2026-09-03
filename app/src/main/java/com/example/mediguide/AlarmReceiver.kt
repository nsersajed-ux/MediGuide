package com.example.mediguide



import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val medicineName = intent.getStringExtra("MEDICINE_NAME") ?: "موعد الدواء"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "medicine_alarm_channel"

        // إنشاء قناة الإشعارات لأندرويد الحديث
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "تنبيهات الأدوية",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "قناة خاصة بتنبيهات مواعيد الأدوية"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // بناء الإشعار
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm) // يمكنك استبدالها بأيقونة تطبيقك إن وجدت
            .setContentTitle("حان موعد الدواء! 💊")
            .setContentText("تذكير بتناول دواء: $medicineName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // إظهار الإشعار
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}