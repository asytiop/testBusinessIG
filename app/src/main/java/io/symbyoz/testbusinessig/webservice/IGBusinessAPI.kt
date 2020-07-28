package io.symbyoz.testbusinessig.webservice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.parse.Parse
import io.symbyoz.testbusinessig.R
import org.json.JSONArray
import org.json.JSONObject

class IGBusinessAPI: AppCompatActivity()
{

    private lateinit var token: String
    private lateinit var igUserId: String
    private lateinit var fbPageId: String

    private lateinit var userMediaIdArray: JSONArray

    private lateinit var loginButton: LoginButton
    private lateinit var callbackManager: CallbackManager

    companion object
    {
        var ctx: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        ctx = applicationContext

        initSDK()
    }

    private fun initSDK()
    {
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        loginButton = findViewById(R.id.login_button)
        loginButton.setReadPermissions("public_profile", "instagram_basic","email","pages_read_engagement","pages_show_list")
        callbackManager = CallbackManager.Factory.create()

        loginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                token = loginResult.accessToken.token.toString()

                Log.d("Main activity", loginResult.accessToken.token.toString())
            }
            override fun onCancel() {
                // App code
            }
            override fun onError(exception: FacebookException) {
                // App code
            }
        })

        getFbPageId()
        getUserId()
        getUserMedia()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*
    Get all replies under a comment
     */
    fun getAllReplies(igCommentId: String) {
        val TAG = "getAllReplies"
        val url  = "graph.facebook.com/" +
                igCommentId +
                "/replies"

        getHttpRequest(url, TAG) {isSuccess, response ->
            if(isSuccess)
            {
                Log.d(TAG, response.toString())
            }
        }

    }

    fun replyComment(igCommentId: String, message: String)
    {
        val TAG = "replyComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "/replies" +
                "?message=" + message

        postHttpRequest(url, TAG)

    }

    fun deleteComment(igCommentId: String)
    {
        val TAG = "deleteComment"
        val url = "graph.facebook.com/" +
                igCommentId

        deleteHttpRequest(url, TAG)
    }

    fun readComment(igCommentId: String)
    {
        val TAG = "readComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "?fields" + "hidden,id,like_count,replies,media,text,timestamp,username"

        getHttpRequest(url, TAG){isSuccess, response ->
            if(isSuccess)
            {
                Log.d(TAG, response.toString())
            }
        }

    }

    /*
    Permissions : instagram_basic, instagram_manage_comments, pages_show_list, pages_read_engagement
    */
    fun hideCommment(igCommentId: String, isHide: Boolean = false)
    {
        val TAG = "hideComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "?hide=" + isHide

        postHttpRequest(url, TAG)
    }

    /*
    Get top media of an hashtag id
     */
    fun getTopMedia(hashtagId: String)
    {
        val TAG = "getTopMedia"
        val url = "graph.facebook.com/" +
                hashtagId + "/top_media" +
                "?user_id=" + igUserId +
                "&fields=" + "caption, comments_count,id ,like_count,media_type,media_url,permalink,timestamp"

        getHttpRequest(url, TAG) {isSuccess, response ->
            if(isSuccess)
            {
                response.toString()
            }
        }

    }

    /*
    Get recent media of an hashtag id
     */
    fun getRecentMedia(hashtagId: String)
    {
        val TAG = "getRecentMedia"
        val url = "graph.facebook.com/" +
                hashtagId + "/recent_media" +
                "?user_id=" + igUserId +
                "&fields=" + "caption, comments_count,id ,like_count,media_type,media_url,permalink,timestamp"

        getHttpRequest(url, TAG){isSuccess, response ->
            if(isSuccess)
            {
                Log.d(TAG, response.toString())
            }
        }
    }

    /*
     Get HashtagId then getTopMedia() and getRecentMedia()
     */
    fun hashtagSearch(hashtag: String)
    {
        val TAG = "hashtagSearch"
        val url = "graph.facebook.com/ig_hashtag_search" +
                "?user_id=" + igUserId +
                "&query=" + hashtag

        getHttpRequest(url, TAG){isSuccess, response ->
            if(isSuccess)
            {
                val data = response.optJSONArray("data")
                val hashtagId = data?.optString(0)

                if (hashtagId != null) {
                    getTopMedia(hashtagId)
                    getRecentMedia(hashtagId)
                }
            }
        }

    }

    /*
    Enable/Disable Comments section on the media
    If not specified, the media comments section will be enabled

    Need instagram_manage_comments permission
     */
    fun enableComments(mediaId: String, enabled: Boolean = true)
    {
        val TAG = "enableComments"
        val url = "graph.facebook.com/" +
                mediaId +
                "?comment_enabled=" + enabled.toString()

        postHttpRequest(url, TAG)

    }

    /*
    Post a comment under the media
     */
    fun postComment(mediaId: String, comment: String)
    {
        val TAG = "postComment"
        val url = "graph.facebook.com/" +
                mediaId + "/comments" +
                "?message=" + comment

        postHttpRequest(url, TAG)
    }

    fun getMediaMetrics(mediaId: String)
    {
        val TAG = "getMediaMetrics"
        val url = "https://graph.facebook.com/v7.0/" + mediaId +
                "?fields=" + "id,username,caption,comments_count,id,is_comment_enabled,like_count,media_type,media_url,permalink,shortcode,thumbnail_url,timestamp" +
                "&access_token=" + token

        getHttpRequest(url, TAG) {isSuccess, response ->
            if(isSuccess)
            {
                ParseAPI.sendMediaMetrics(response)
            }
        }
    }

    fun getUserData()
    {
        val TAG = "getUserData"
        val url = "https://graph.facebook.com/" +
                igUserId +
                "?fields=" + "ig_id,name,username,biography,followers_count,follow_count,media_count,profile_picture_url" +
                "&access_token" + token

        getHttpRequest(url, TAG) {isSuccess, response ->
            if(isSuccess)
            {
                ParseAPI.sendUserData(response)
            }
        }
    }

    /*
    metricsPeriod :"day","week","days_28"
     */
    fun getUserMetrics(metricsPeriod: String)
    {
        val TAG = "getAccountMetrics"
        val url = "https://graph.facebook.com/v7.0/"+
                igUserId + "/insights" +
                "?metrics=" + "audience_city,audience_country,audience_gender_age,audience_locale,email_contacts,follower_count," +
                "get_directions_clicks,impressions,online_followers,phone_call_clicks,profile_views,reach,text_message_clicks,website_clicks" +
                "&period=" + metricsPeriod +
                //"&since=" + since +
                //"&until=" + until +
                "&access_token=" + token


        getHttpRequest(url, TAG) { isSuccess, response ->
            if(isSuccess)
            {
                ParseAPI.sendUserMetrics(response)
            }
        }
    }

    private fun getHttpRequest(url: String, TAG: String, listener: (isSuccess: Boolean, response: JSONObject) -> Unit)
    {
        val queue = Volley.newRequestQueue(this)

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
    }

    private fun postHttpRequest(url: String, TAG: String)
    {
        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.POST, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }

    private fun deleteHttpRequest(url: String, TAG: String)
    {
        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.DELETE, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }

    //Max can get : 10K media
    private fun getUserMedia()
    {
        val TAG = "getUserMedia"
        val url = "https://graph.facebook.com/" +
                igUserId +
                "/media"

        getHttpRequest(url, TAG) {isSuccess, response ->
            if(isSuccess)
            {
                userMediaIdArray = response.optJSONArray("data")
            }
        }
    }

    /*
    Need a valid fbPageId
    */
    private fun getUserId()
    {
        val TAG = "getInstagramPageId"
        val url = "https://graph.facebook.com/v7.0/" +
                fbPageId +
                "?fields="+"instagram_business_account" +
                "&access_token=" + token


        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)
                val igBusinessAccount = resJSON.optJSONObject("instagram_business_account")

                igUserId = igBusinessAccount.optString("id")

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }

    /*

     */
    private fun getFbPageId()
    {
        val TAG = "getFbUserPages"
        val url = "https://graph.facebook.com/v7.0/me/accounts" +
                "?access_token=" + token

        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)
                val userPagesData = resJSON.optJSONObject("data")

                fbPageId = userPagesData.optString("id")

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }
}