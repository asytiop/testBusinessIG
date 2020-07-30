package io.symbyoz.testbusinessig.webservice

import android.util.Log
import com.parse.ParseException
import io.symbyoz.testbusinessig.model.*
import org.json.JSONObject

class ParseAPI
{
    companion object
    {
        fun sendUserData(userDataJSON: JSONObject)
        {
            val userData = UserData()

            userData.userData = userDataJSON.optJSONArray("data")

            userData.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendUserData() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendUserData() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }

        fun sendMediaMetrics(mediaMetricsJSON: JSONObject)
        {
            val mediaMetrics = MediaMetrics()

            mediaMetrics.mediaMetrics = mediaMetricsJSON.optJSONArray("data")

            mediaMetrics.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendMediaMetrics() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendMediaMetrics() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }

        fun sendUserDailyMetrics(userDailyMetricsJSON: JSONObject)
        {
            val userDailyMetrics = UserDailyMetrics()

            userDailyMetrics.dailyMetrics = userDailyMetricsJSON.optJSONArray("data")
        }

        fun sendUserWeeklyMetrics(userWeeklyMetricsJSON: JSONObject)
        {
            val userWeeklyMetrics = UserWeeklyMetrics()

            userWeeklyMetrics.weeklyMetrics = userWeeklyMetricsJSON.optJSONArray("data")
        }

        fun sendUserMonthlyMetrics(userMonthlyMetricsJSON: JSONObject)
        {
            val userMonthlyMetrics = UserMonthlyMetrics()

            userMonthlyMetrics.monthlyMetrics = userMonthlyMetricsJSON.optJSONArray("data")
        }

        fun sendUserLifetimeMetrics(userLifetimeMetricsJSON: JSONObject)
        {
            val userLifetimeMetrics = UserLifetimeMetrics()

            userLifetimeMetrics.lifetimeMetrics = userLifetimeMetricsJSON.optJSONArray("data")
        }
    }
}