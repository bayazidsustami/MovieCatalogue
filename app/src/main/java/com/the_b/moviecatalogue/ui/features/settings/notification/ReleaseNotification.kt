package com.the_b.moviecatalogue.ui.features.settings.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.paging.ExperimentalPagingApi
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.ui.details.DescActivity
import com.the_b.moviecatalogue.ui.main.MainActivity
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.FilmModelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReleaseNotification : BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = 103
        const val CHANNEL_ID = "channel_release"
        const val CHANNEL_NAME = "Release Channel"
        const val MAX_NOTIFICATION = 2
        const val RELEASE_CHANNEL_GROUP = "release_group"
    }

    private var filmList = ArrayList<FilmModel>()
    private var filmNotifList = ArrayList<FilmModel>()

    @ExperimentalPagingApi
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        loadData(context)
    }

    @ExperimentalPagingApi
    private fun loadData(context: Context){
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(date)
        val apiService = ApiBuilder.createService(ApiService::class.java)
        val call = apiService.releaseFilm(currentDate, currentDate)
        call.enqueue(object : Callback<FilmModelResponse>{
            override fun onFailure(call: Call<FilmModelResponse>, t: Throwable) {
                Toast.makeText(context, "Failed getting data : ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<FilmModelResponse>,
                response: Response<FilmModelResponse>
            ) {
                val data = response.body() as FilmModelResponse
                filmNotifList.clear()
                filmList.clear()
                filmList.addAll(data.results)

                filmList.forEach {
                    showAlarmNotification(context, it)
                }
            }

        })
    }

    @ExperimentalPagingApi
    private fun showAlarmNotification(context: Context, film: FilmModel){
        filmNotifList.add(film)

        val builder: NotificationCompat.Builder

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (filmNotifList.size < MAX_NOTIFICATION){
            val intent = Intent(context, DescActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(DescActivity.EXTRA_DATA, film.id)
            val pendingIntent = PendingIntent.getActivity(context, film.id!!, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Movie Catalogue")
                .setContentText("New Film Release${film.title}")
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setVibrate(longArrayOf(1000,1000,1000))
                .setSound(alarmSound)
                .setGroup(RELEASE_CHANNEL_GROUP)
                .setGroupSummary(true)
                .setAutoCancel(true)
        } else {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            val  pendingIntent = PendingIntent.getActivity(context, film.id!!, intent, 0)

            val inboxStyle = NotificationCompat.InboxStyle()
                .setBigContentTitle("New Films")
                .setSummaryText("${filmNotifList.size} film release today")
            filmNotifList.forEach {
                inboxStyle.addLine("New Film : ${it.title}")
            }

            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Movie Catalogue")
                .setContentText("${filmNotifList.size} film release today")
                .setGroup(RELEASE_CHANNEL_GROUP)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setVibrate(longArrayOf(1000,1000,1000))
                .setSound(alarmSound)
                .setAutoCancel(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun setRepeatingAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseNotification::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Success setup alarm", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseNotification::class.java)
        val requestCode = NOTIFICATION_ID
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Success cancel alarm", Toast.LENGTH_SHORT).show()
    }
}
