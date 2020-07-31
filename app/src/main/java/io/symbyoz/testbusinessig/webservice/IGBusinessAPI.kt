package io.symbyoz.testbusinessig.webservice

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import org.json.JSONArray
import org.json.JSONObject

open class IGBusinessAPI(private val context: Context) {

    protected lateinit var token: String

    protected lateinit var igUserId: String
    protected lateinit var fbPageId: String

    protected lateinit var userMediaIdArray: JSONArray

    private var queue = Volley.newRequestQueue(context)

    var isReady: Boolean = false

    fun initWithToken(accessToken: String) {
        val TAG: String = "initWithToken"

        token = accessToken
        getFbPageId()

        Log.d(TAG, token)
    }

    //
    // MANAGE COMMENT
    // ============================================================================================

    /*
    Get all replies under a comment
     */
    fun getAllReplies(igCommentId: String) {
        val TAG = "getAllReplies"
        val url = "graph.facebook.com/" +
                igCommentId +
                "/replies"

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                Log.d(TAG, response.toString())
            }
        }

    }

    /*
    Enable/Disable Comments section on the media
    If not specified, the media comments section will be enabled

    Need instagram_manage_comments permission
     */
    fun enableComments(mediaId: String, enabled: Boolean = true) {
        val TAG = "enableComments"
        val url = "graph.facebook.com/" +
                mediaId +
                "?comment_enabled=" + enabled.toString()

        postHttpRequest(url, TAG)
    }

    /*
    Post a comment under the media
     */
    fun postComment(mediaId: String, comment: String) {
        val TAG = "postComment"
        val url = "graph.facebook.com/" +
                mediaId + "/comments" +
                "?message=" + comment

        postHttpRequest(url, TAG)
    }

    fun replyComment(igCommentId: String, message: String) {
        val TAG = "replyComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "/replies" +
                "?message=" + message

        postHttpRequest(url, TAG)

    }

    fun deleteComment(igCommentId: String) {
        val TAG = "deleteComment"
        val url = "graph.facebook.com/" +
                igCommentId

        deleteHttpRequest(url, TAG)
    }

    fun readComment(igCommentId: String) {
        val TAG = "readComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "?fields" + "hidden,id,like_count,replies,media,text,timestamp,username"

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                Log.d(TAG, response.toString())
            }
        }

    }

    /*
    Permissions : instagram_basic, instagram_manage_comments, pages_show_list, pages_read_engagement
    */
    fun hideCommment(igCommentId: String, isHide: Boolean = false) {
        val TAG = "hideComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "?hide=" + isHide

        postHttpRequest(url, TAG)
    }

    //
    // HASHTAG SEARCH
    // ============================================================================================

    /*
    Get top media of an hashtag id
     */
    fun getTopMedia(hashtagId: String) {
        val TAG = "getTopMedia"
        val url = "graph.facebook.com/" +
                hashtagId + "/top_media" +
                "?user_id=" + igUserId +
                "&fields=" + "caption, comments_count,id ,like_count,media_type,media_url,permalink,timestamp"

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                response.toString()
            }
        }

    }

    /*
    Get recent media of an hashtag id
     */
    fun getRecentMedia(hashtagId: String) {
        val TAG = "getRecentMedia"
        val url = "graph.facebook.com/" +
                hashtagId + "/recent_media" +
                "?user_id=" + igUserId +
                "&fields=" + "caption, comments_count,id ,like_count,media_type,media_url,permalink,timestamp"

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                Log.d(TAG, response.toString())
            }
        }
    }

    /*
     Get HashtagId then getTopMedia() and getRecentMedia()
     */
    fun hashtagSearch(hashtag: String) {
        val TAG = "hashtagSearch"
        val url = "graph.facebook.com/ig_hashtag_search" +
                "?user_id=" + igUserId +
                "&query=" + hashtag

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                val data = response.optJSONArray("data")
                val hashtagId = data?.optString(0)

                if (hashtagId != null) {
                    getTopMedia(hashtagId)
                    getRecentMedia(hashtagId)
                }
            }
        }

    }


    //
    // GET DATA AND METRICS METHODS
    // ============================================================================================

    /*
    Get media metrics on all User Media
     */
    fun getAllMediaMetrics() {
        val TAG = "getAllMediaMetrics"

        for (i in 0 until userMediaIdArray.length()) {
            getMediaMetrics(userMediaIdArray.optJSONObject(i).optString("id"))
        }

        Log.d(TAG, userMediaIdArray.toString())
    }

    /*
    Get media metrics with media_id
     */
    private fun getMediaMetrics(mediaId: String) {
        val TAG = "getMediaMetrics"
        val url = "https://graph.facebook.com/v7.0/" + mediaId +
                "?fields=" + "id,username,caption,comments_count,is_comment_enabled,like_count,media_type,media_url,permalink,shortcode,thumbnail_url,timestamp" +
                "&access_token=" + token

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                ParseAPI.sendMediaMetrics(response)
            }
        }
    }

    fun getUserData() {
        val TAG = "getUserData"
        val url = "https://graph.facebook.com/" +
                igUserId +
                "?fields=" + "ig_id,name,username,biography,followers_count,follows_count,media_count,profile_picture_url" +
                "&access_token=" + token

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                ParseAPI.sendUserData(response)
            }
        }
    }

    fun getAllUserMetrics() {
        getUserMetricsByPeriod("day")
        getUserMetricsByPeriod("week")
        getUserMetricsByPeriod("days_28")
        getUserMetricsByPeriod("lifetime")
    }

    /*
    metricsPeriod :"day","week","days_28","lifetime"
     */
    fun getUserMetricsByPeriod(metricsPeriod: String) {
        val TAG = "getAccountMetrics"
        var metric = ""

        when (metricsPeriod) {
            "day" -> metric =
                "email_contacts,follower_count,get_directions_clicks,impressions,phone_call_clicks,profile_views,reach,text_message_clicks,website_clicks"
            "week" -> metric = "impressions, reach"
            "days_28" -> metric = "impressions, reach"
            "lifetime" -> metric =
                "audience_city,audience_country,audience_gender_age,audience_locale,online_followers"
        }

        val url = "https://graph.facebook.com/v7.0/" +
                igUserId + "/insights" +
                "?metric=" + metric +
                "&period=" + metricsPeriod +
                //"&since=" + since +
                //"&until=" + until +
                "&access_token=" + token


        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                Log.d(TAG, response.toString())
                when (metricsPeriod) {
                    "day" -> ParseAPI.sendUserDailyMetrics(response)
                    "week" -> ParseAPI.sendUserWeeklyMetrics(response)
                    "days_28" -> ParseAPI.sendUserMonthlyMetrics(response)
                    "lifetime" -> ParseAPI.sendUserLifetimeMetrics(response)
                }
            }
        }
    }


    //
    // INIT METHODS
    // ============================================================================================

    //Max can get : 10K media
    private fun getUserMedia() {
        val TAG = "getUserMedia"
        val url = "https://graph.facebook.com/v7.0/" +
                igUserId +
                "/media" +
                "?access_token=" + token

        getHttpRequest(url, TAG) { isSuccess, response ->
            if (isSuccess) {
                userMediaIdArray = response.optJSONArray("data")
                isReady = true
            }
        }
    }

    /*
    Need a valid fbPageId
    */
    private fun getUserId() {
        val TAG = "getInstagramPageId"
        val url = "https://graph.facebook.com/v7.0/" +
                fbPageId +
                "?fields=" + "instagram_business_account" +
                "&access_token=" + token

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)
                val igBusinessAccount = resJSON.optJSONObject("instagram_business_account")

                igUserId = igBusinessAccount.optString("id")

                getUserMedia()

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error -> Log.d(TAG, error.toString()) })

        queue.add(stringRequest)
    }

    /*

     */
    private fun getFbPageId() {
        val TAG = "getFbUserPages"
        val url = "https://graph.facebook.com/v7.0/me/accounts" +
                "?access_token=" + token


        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                val userPagesData = resJSON.optJSONArray("data")
                    .toJSONObject(JSONArray().put("as_JSONObject_values"))
                    .optJSONObject("as_JSONObject_values")

                fbPageId = userPagesData.optString("id")

                getUserId()

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error -> Log.d(TAG, error.toString()) })

        queue.add(stringRequest)
    }

    //
    // HTTP REQUEST METHODS
    // ============================================================================================

    private fun getHttpRequest(
        url: String,
        TAG: String,
        listener: (isSuccess: Boolean, response: JSONObject) -> Unit
    ) {

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)
                Log.d(TAG, resJSON.toString())
                listener.invoke(true, resJSON)

            }, Response.ErrorListener { error ->

                Log.d(TAG, error.toString())
                listener.invoke(false, JSONObject())

            })

        queue.add(stringRequest)

        Log.d("getHTTPRequest() ::", queue.toString())
    }

    private fun postHttpRequest(url: String, TAG: String) {

        val stringRequest =
            StringRequest(Request.Method.POST, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error -> Log.d(TAG, error.toString()) })

        queue.add(stringRequest)
    }

    private fun deleteHttpRequest(url: String, TAG: String) {

        val stringRequest =
            StringRequest(Request.Method.DELETE, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error -> Log.d(TAG, error.toString()) })

        queue.add(stringRequest)
    }

    inner class OnFacebookCallback : FacebookCallback<LoginResult> {
        val TAG: String = "IGBusinessAPI"

        override fun onSuccess(result: LoginResult) {
            token = result.accessToken.token.toString()
            Log.d(TAG, "OnFacebookCallback :: " + result.accessToken.token.toString())
            getFbPageId()
        }

        override fun onCancel() {
            Toast.makeText(context, "Login canceled", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "OnFacebookCallback :: onCancel()")
        }

        override fun onError(error: FacebookException?) {
            Toast.makeText(context, "Login Error", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "OnFacebookCallBack :: " + error.toString())
        }

    }
}

