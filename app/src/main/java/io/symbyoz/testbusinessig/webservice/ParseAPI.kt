package io.symbyoz.testbusinessig.webservice

import android.util.Log
import com.parse.ParseException
import com.parse.ktx.delegates.booleanAttribute
import com.parse.ktx.delegates.intAttribute
import com.parse.ktx.delegates.jsonArrayAttribute
import com.parse.ktx.delegates.stringAttribute
import io.symbyoz.testbusinessig.model.MediaMetrics
import io.symbyoz.testbusinessig.model.UserData
import io.symbyoz.testbusinessig.model.UserMetrics
import org.json.JSONArray

class ParseAPI
{
    companion object
    {
        fun sendUserData(userId: String, name: String, userName: String, biography: String,
                         followersCount: String, followCount: String, media_count: String,
                         profile_picture_url: String)
        {
            val userData = UserData()

            userData.user_id = userId
            userData.name = name
            userData.username = userName
            userData.biography = biography
            userData.followers_count = followersCount
            userData.follow_count = followCount
            userData.media_count = media_count
            userData.profile_picture_url = profile_picture_url

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

        fun sendMediaMetrics(
            media_id: String, username: String, caption: String, comments_count: Int, is_comment_enabled: Boolean,
            like_count: Int, media_type: String, media_url: String, permalink: String, shortcode: String,
            timestamp: String)
        {
            val mediaMetrics = MediaMetrics()

            mediaMetrics.media_id = media_id
            mediaMetrics.username = username
            mediaMetrics.caption = caption
            mediaMetrics.comments_count = comments_count
            mediaMetrics.is_comment_enabled = is_comment_enabled
            mediaMetrics.like_count = like_count
            mediaMetrics.media_type = media_type
            mediaMetrics.media_url = media_url
            mediaMetrics.permalink = permalink
            mediaMetrics.shortcode = shortcode
            mediaMetrics.timestamp = timestamp

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

        fun sendUserMetrics(audience_city: JSONArray, audience_country: JSONArray, audience_gender_age: JSONArray,
                            audience_locale: JSONArray, email_contacts: Int, follower_count: Int, get_directions_clicks: Int, impressions: Int,
                            online_followers: Int, phone_call_clicks: Int, profile_views: Int, reach: Int,
                            text_message_clicks: Int, website_clicks: Int)
        {
            val userMetrics = UserMetrics()

            userMetrics.audience_city = audience_city
            userMetrics.audience_country = audience_country
            userMetrics.audience_gender_age = audience_gender_age
            userMetrics.audience_locale = audience_locale
            userMetrics.email_contacts = email_contacts
            userMetrics.follower_count = follower_count
            userMetrics.get_directions_clicks = get_directions_clicks
            userMetrics.impressions = impressions
            userMetrics.online_followers = online_followers
            userMetrics.phone_call_clicks = phone_call_clicks
            userMetrics.profile_views = profile_views
            userMetrics.reach = reach
            userMetrics.text_message_clicks = text_message_clicks
            userMetrics.website_clicks = website_clicks


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