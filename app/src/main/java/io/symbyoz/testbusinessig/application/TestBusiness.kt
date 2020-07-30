package io.symbyoz.testbusinessig.application

import android.app.Application
import android.content.Context
import android.util.Log
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.parse.Parse
import com.parse.ParseObject
import io.symbyoz.testbusinessig.core.PARSE
import io.symbyoz.testbusinessig.model.*

class TestBusiness : Application() {

    companion object
    {
        const val TAG = "TestApp"
        var ctx: Context? = null
    }


    override fun onCreate()
    {
        super.onCreate()
        ctx = applicationContext

        initSDK()
    }


    private fun initSDK()
    {
        Log.d(TAG, "initSDK()")

        //FACEBOOK API
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        ParseObject.registerSubclass(UserData::class.java)
        ParseObject.registerSubclass(UserDailyMetrics::class.java)
        ParseObject.registerSubclass(UserWeeklyMetrics::class.java)
        ParseObject.registerSubclass(UserMonthlyMetrics::class.java)
        ParseObject.registerSubclass(UserLifetimeMetrics::class.java)
        ParseObject.registerSubclass(MediaMetrics::class.java)

        // PARSE
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(PARSE.APPLICATION_ID) // if desired
                .clientKey(PARSE.CLIENT_KEY)
                .server(PARSE.SERVER)
                .enableLocalDataStore()
                .build()
        )
    }

}