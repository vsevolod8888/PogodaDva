package com.example.pogoda2.foreground_service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.work.*
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.example.pogoda2.MainActivity
import com.example.pogoda2.R
import com.example.pogoda2.repozitory_and_dataanswer.Repository
import com.example.pogoda2.repozitory_and_dataanswer.getDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import retrofit2.HttpException
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToInt

class ForegroundService() : LifecycleService(),CoroutineScope {
    var job:Job?=null
    var LOG:String = "LOG"
    override val coroutineContext: CoroutineContext=Dispatchers.Main+ SupervisorJob()
    //private val applicationScope = CoroutineScope(Dispatchers.Default)
    private val CHANNEL_ID = "ForegroundService Kotlin"
    val repppppp: Repository by lazy { Repository(getDatabase(this)) }                // экз объекта созд при первом обращении
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            applicationContext
        )
    }

    companion object {
        var timeRepeat: Long? = null
        var isClickedNotifications: Boolean = false
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
            isClickedNotifications == true
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
            isClickedNotifications == false
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
                                                                                // запускаем worker (periodic)
        // ост
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
        repppppp.weather.observe(this, Observer {
            val temp = it[0].tempDay
            createNotificationChannel()
            val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
            )
            val bitmapLargeIcon = BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.ic_main_iconnn
            )

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Температура сейчас:")
                .setContentText(
                    temp.roundToInt().toString() + "°C"
                )                // наша перданная температура
                .setSmallIcon(getIconByTemperature(temp.roundToInt()))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmapLargeIcon)
                .build()
            startForeground(1, notification)
        })

        //stopSelf();

    }

    //делаем метод гетиконбутемп, кот возвр инт(ссылку на рессурс иконки)
    private fun getIconByTemperature(temp: Int): Int {
        return when (temp) {
            -45 -> R.drawable.ic_stat__45
            -44 -> R.drawable.ic_stat__44
            -43 -> R.drawable.ic_stat__43
            -42 -> R.drawable.ic_stat__42
            -41 -> R.drawable.ic_stat__41
            -40 -> R.drawable.ic_stat__40
            -39 -> R.drawable.ic_stat__39
            -38 -> R.drawable.ic_stat__38
            -37 -> R.drawable.ic_stat__37
            -36 -> R.drawable.ic_stat__36
            -35 -> R.drawable.ic_stat__35
            -34 -> R.drawable.ic_stat__34
            -33 -> R.drawable.ic_stat__33
            -32 -> R.drawable.ic_stat__32
            -31 -> R.drawable.ic_stat__31
            -30 -> R.drawable.ic_stat__30
            -29 -> R.drawable.ic_stat__29
            -28 -> R.drawable.ic_stat__28
            -27 -> R.drawable.ic_stat__27
            -26 -> R.drawable.ic_stat__26
            -25 -> R.drawable.ic_stat__25
            -24 -> R.drawable.ic_stat__24
            -23 -> R.drawable.ic_stat__23
            -22 -> R.drawable.ic_stat__22
            -21 -> R.drawable.ic_stat__21
            -20 -> R.drawable.ic_stat__20
            -19 -> R.drawable.ic_stat__19
            -18 -> R.drawable.ic_stat__18
            -17 -> R.drawable.ic_stat__17
            -16 -> R.drawable.ic_stat__16
            -15 -> R.drawable.ic_stat__15
            -14 -> R.drawable.ic_stat__14
            -13 -> R.drawable.ic_stat__13
            -12 -> R.drawable.ic_stat__12
            -11 -> R.drawable.ic_stat__11
            -10 -> R.drawable.ic_stat__10
            -9 -> R.drawable.ic_stat__9
            -8 -> R.drawable.ic_stat__8
            -7 -> R.drawable.ic_stat__7
            -6 -> R.drawable.ic_stat__6
            -5 -> R.drawable.ic_stat__5
            -4 -> R.drawable.ic_stat__4
            -3 -> R.drawable.ic_stat__3
            -2 -> R.drawable.ic_stat__2
            -1 -> R.drawable.ic_stat__1
            0 -> R.drawable.ic_stat_0
            1 -> R.drawable.ic_stat_1
            2 -> R.drawable.ic_stat_2
            3 -> R.drawable.ic_stat_3
            4 -> R.drawable.ic_stat_4
            5 -> R.drawable.ic_stat_5
            6 -> R.drawable.ic_stat_6
            7 -> R.drawable.ic_stat_7
            8 -> R.drawable.ic_stat_8
            9 -> R.drawable.ic_stat_9
            10 -> R.drawable.ic_stat_10
            11 -> R.drawable.ic_stat_11
            12 -> R.drawable.ic_stat_12
            13 -> R.drawable.ic_stat_13
            14 -> R.drawable.ic_stat_14
            15 -> R.drawable.ic_stat_15
            16 -> R.drawable.ic_stat_16
            17 -> R.drawable.ic_stat_17
            18 -> R.drawable.ic_stat_18
            19 -> R.drawable.ic_stat_19
            20 -> R.drawable.ic_stat_20
            21 -> R.drawable.ic_stat_21
            22 -> R.drawable.ic_stat_22
            23 -> R.drawable.ic_stat_23
            24 -> R.drawable.ic_stat_24
            25 -> R.drawable.ic_stat_25
            26 -> R.drawable.ic_stat_26
            27 -> R.drawable.ic_stat_27
            28 -> R.drawable.ic_stat_28
            29 -> R.drawable.ic_stat_29
            30 -> R.drawable.ic_stat_30
            31 -> R.drawable.ic_stat_31
            32 -> R.drawable.ic_stat_32
            33 -> R.drawable.ic_stat_33
            34 -> R.drawable.ic_stat_34
            35 -> R.drawable.ic_stat_35
            36 -> R.drawable.ic_stat_36
            37 -> R.drawable.ic_stat_37
            38 -> R.drawable.ic_stat_38
            39 -> R.drawable.ic_stat_39
            40 -> R.drawable.ic_stat_40
            41 -> R.drawable.ic_stat_41
            42 -> R.drawable.ic_stat_42
            43 -> R.drawable.ic_stat_43
            44 -> R.drawable.ic_stat_44
            45 -> R.drawable.ic_stat_45
            else -> R.drawable.ic_notif_image_good
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun delayedInit() {
        timeRepeat?.let { setupRecurringWork(it) }

    }

    private fun setupRecurringWork(m:Long) {
        job?.cancel()

        val tickerChannel = ticker(delayMillis = m, initialDelayMillis = 5000)
        Log.d(LOG, "Запущено RecurentWork")
        job=launch {
            for (event in tickerChannel) {
                try {
                    Log.d(LOG, "Вкл. минута")
                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION                                            // грубая локация
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION                                          // точная локация
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return@launch
                    }
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            val lattt: Double? = location?.latitude
                            val loggg: Double? = location?.longitude
                            Log.d(LOG, "Вкл. successListener")
                            launch {
                                repppppp.refreshWeather2(lattt.toString(), loggg.toString())
                                Log.d(
                                    GlideExecutor.UncaughtThrowableStrategy.LOG.toString(),
                                    "WorkManager: Work request for sync is run"
                                )
                            }
                        }

                } catch (e: HttpException) {

                }
            }
        }

        //delay(1000)

// when you're done with the ticker and don't want more events
      //  tickerChannel.cancel()
    }


    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}