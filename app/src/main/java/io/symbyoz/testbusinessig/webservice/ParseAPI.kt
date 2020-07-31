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

            userData.userData = userDataJSON.toString()

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

            mediaMetrics.mediaMetrics = mediaMetricsJSON.toString()

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

            userDailyMetrics.dailyMetrics = userDailyMetricsJSON.optJSONArray("data").toString()

            userDailyMetrics.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendUserDailyMetrics() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendUserDailyMetrics() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }

        fun sendUserWeeklyMetrics(userWeeklyMetricsJSON: JSONObject)
        {
            val userWeeklyMetrics = UserWeeklyMetrics()

            userWeeklyMetrics.weeklyMetrics = userWeeklyMetricsJSON.optJSONArray("data").toString()

            userWeeklyMetrics.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendUserWeeklyMetrics() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendUserWeeklyMetrics() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }

        fun sendUserMonthlyMetrics(userMonthlyMetricsJSON: JSONObject)
        {
            val userMonthlyMetrics = UserMonthlyMetrics()

            userMonthlyMetrics.monthlyMetrics = userMonthlyMetricsJSON.optJSONArray("data").toString()

            userMonthlyMetrics.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendUserMonthlyMetrics() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendUserMonthlyMetrics() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }

        fun sendUserLifetimeMetrics(userLifetimeMetricsJSON: JSONObject)
        {
            val userLifetimeMetrics = UserLifetimeMetrics()

            userLifetimeMetrics.lifetimeMetrics = userLifetimeMetricsJSON.optJSONArray("data").toString()

            Log.d("sendLifeMetrics :: ", userLifetimeMetrics.lifetimeMetrics)

            userLifetimeMetrics.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendUserLifetimeMetrics() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendUserLifetimeMetrics() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }
    }
}