package io.symbyoz.testbusinessig.webservice

import android.util.Log
import com.parse.ParseException
import io.symbyoz.testbusinessig.model.MediaMetrics
import io.symbyoz.testbusinessig.model.UserData
import io.symbyoz.testbusinessig.model.UserMetrics
import org.json.JSONObject

class ParseAPI
{
    companion object
    {
        fun sendUserData(userDataJSON: JSONObject)
        {
            val userData = UserData()

            userData.user_id = userDataJSON.optString("ig_id")
            userData.name = userDataJSON.optString("name")
            userData.username = userDataJSON.optString("username")
            userData.biography = userDataJSON.optString("biography")
            userData.followers_count = userDataJSON.optString("followers_count")
            userData.follow_count = userDataJSON.optString("follow_count")
            userData.media_count = userDataJSON.optString("media_count")
            userData.profile_picture_url = userDataJSON.optString("profile_picture_url")

            Log.d("sendUserData()", userData.getString("client_id"))

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

            mediaMetrics.media_id = mediaMetricsJSON.optString("id")
            mediaMetrics.username = mediaMetricsJSON.optString("username")
            mediaMetrics.caption = mediaMetricsJSON.optString("caption")
            mediaMetrics.comments_count = mediaMetricsJSON.optInt("comments_count")
            mediaMetrics.is_comment_enabled = mediaMetricsJSON.optBoolean("is_comment_enabled")
            mediaMetrics.like_count = mediaMetricsJSON.optInt("like_count")
            mediaMetrics.media_type = mediaMetricsJSON.optString("media_type")
            mediaMetrics.media_url = mediaMetricsJSON.optString("media_url")
            mediaMetrics.permalink = mediaMetricsJSON.optString("permalink")
            mediaMetrics.shortcode = mediaMetricsJSON.optString("shortcode")
            mediaMetrics.timestamp = mediaMetricsJSON.optString("timestamp")

            Log.d("sendMediaMetrics()", mediaMetrics.getString("client_id"))

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

        fun sendUserMetrics(userMetricsJSON: JSONObject)
        {
            val userMetrics = UserMetrics()

            userMetrics.audience_city = userMetricsJSON.optJSONArray("audience_city")
            userMetrics.audience_country = userMetricsJSON.optJSONArray("audience_country")
            userMetrics.audience_gender_age = userMetricsJSON.optJSONArray("audience_gender_age")
            userMetrics.audience_locale = userMetricsJSON.optJSONArray("audience_locale")
            userMetrics.email_contacts = userMetricsJSON.optInt("email_contacts")
            userMetrics.follower_count = userMetricsJSON.optInt("follower_count")
            userMetrics.get_directions_clicks = userMetricsJSON.optInt("get_directions_clicks")
            userMetrics.impressions = userMetricsJSON.optInt("impressions")
            userMetrics.online_followers = userMetricsJSON.optInt("online_followers")
            userMetrics.phone_call_clicks = userMetricsJSON.optInt("phone_call_clicks")
            userMetrics.profile_views = userMetricsJSON.optInt("profile_view")
            userMetrics.reach = userMetricsJSON.optInt("reach")
            userMetrics.text_message_clicks = userMetricsJSON.optInt("text_message_clicks")
            userMetrics.website_clicks = userMetricsJSON.optInt("website_clicks")


            Log.d("sendUserMetrics()", userMetrics.toString())

            userMetrics.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendUserMetrics() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendUserMetrics() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }
    }
}