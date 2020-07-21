package io.symbyoz.testbusinessig.activity

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import io.symbyoz.testbusinessig.R
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: LoginButton
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        loginButton = findViewById(R.id.login_button)
        loginButton.setReadPermissions("public_profile", "instagram_basic","email","pages_read_engagement","pages_show_list")
        callbackManager = CallbackManager.Factory.create()

        loginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                val token = loginResult.accessToken.token.toString()

                Log.d("Main activity", loginResult.accessToken.token.toString())
                getFbUserPages(token)
            }
            override fun onCancel() {
                // App code// App code
            }
            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getHttpRequest(url: String, TAG: String)
    {
        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                /*sendUserInfo(resJSON.optString("id"),
                    resJSON.optString("username"),
                    resJSON.optInt("media_count"))*/

                Log.d(TAG, resJSON.toString())
            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }

    private fun postHttpRequest(url: String, TAG: String)
    {
        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.POST, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                /*sendUserInfo(resJSON.optString("id"),
                    resJSON.optString("username"),
                    resJSON.optInt("media_count"))*/

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

                /*sendUserInfo(resJSON.optString("id"),
                    resJSON.optString("username"),
                    resJSON.optInt("media_count"))*/

                Log.d(TAG, resJSON.toString())

            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }

    /*
    Get all replies on a comment
     */
    private fun getAllReplies(igCommentId: String)
    {
        val TAG = "getAllReplies"
        val url  = "graph.facebook.com/" +
                igCommentId +
                "/replies"

        getHttpRequest(url, TAG)

    }

    private fun replyComment(igCommentId: String, message: String)
    {
        val TAG = "replyComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "/replies" +
                "?message=" + message

        postHttpRequest(url, TAG)

    }

    private fun deleteComment(igCommentId: String)
    {
        val TAG = "deleteComment"
        val url = "graph.facebook.com/" +
                igCommentId

        deleteHttpRequest(url, TAG)
    }

    private fun readComment(igCommentId: String)
    {
        val TAG = "readComment"
        val url = "graph.facebook.com/" +
                igCommentId +
                "?fields" + "hidden,id,like_count,replies,media,text,timestamp,username"

        getHttpRequest(url, TAG)

    }

    /*
    Permissions : instagram_basic, instagram_manage_comments, pages_show_list, pages_read_engagement
    */
    private fun hideCommment(igCommentId: String, isHide: Boolean = false)
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
    private fun getTopMedia(userId: String, hashtagId: String)
    {
        val TAG = "getTopMedia"
        val url = "graph.facebook.com/" +
                hashtagId + "/top_media" +
                "?user_id=" + userId +
                "&fields=" + "caption, comments_count,id ,like_count,media_type,media_url,permalink,timestamp"

        getHttpRequest(url, TAG)

    }

    /*
    Get recent media of an hashtag id
     */
    private fun getRecentMedia(userId: String, hashtagId: String)
    {
        val TAG = "getRecentMedia"
        val url = "graph.facebook.com/" +
                hashtagId + "/recent_media" +
                "?user_id=" + userId +
                "&fields=" + "caption, comments_count,id ,like_count,media_type,media_url,permalink,timestamp"

        getHttpRequest(url, TAG)
    }

    /*
    getRecentMedia() and getTopMedia() of with hashtag results id
     */
    private fun hashtagSearch(userId: String, hashtag: String)
    {
        val TAG = "hashtagSearch"
        val url = "graph.facebook.com/ig_hashtag_search" +
                "?user_id=" + userId +
                "&query=" + hashtag

        getHttpRequest(url, TAG)

    }

    /*
    Enable/Disable Comments section on the media
    If not specified, the media comments section will be enabled

    Need instagram_manage_comments permission
     */
    private fun enableComments(mediaId: String, enabled: Boolean = true)
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
    private fun postComment(mediaId: String, comment: String)
    {
        val TAG = "postComment"
        val url = "graph.facebook.com/" +
                mediaId + "/comments" +
                "?message=" + comment

        postHttpRequest(url, TAG)
    }

    private fun getMediaMetrics(token: String, mediaId: String)
    {
        val TAG = "getMediaMetrics"
        val url = "https://graph.facebook.com/v7.0/" + mediaId +
        "?fields=" + "id,username,caption,comments_count,id,is_comment_enabled,like_count,media_type,media_url,permalink,shortcode,thumbnail_url,timestamp" +
        "&access_token=" + token

        getHttpRequest(url, TAG)
    }

    //Max can get : 10K media
    private fun getUserMedia(token: String, igUserId: String)
    {
        val TAG = "getUserMedia"
        val url = "https://graph.facebook.com/" +
                 igUserId +
                "/media"

        getHttpRequest(url, TAG)
    }

    private fun getUserData(token: String, igUserId: String)
    {
        val TAG = "getUserData"
        val url = "https://graph.facebook.com/" +
                igUserId +
                "?fields=" + "ig_id,name,username,biography,followers_count,follow_count,media_count,profile_picture_url" +
                "&access_token" + token

        getHttpRequest(url, TAG)
    }

    private fun getInstagramPageId(token: String, fbPageId: String)
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
                val igPageId = igBusinessAccount.optString("id")

                /*sendUserInfo(resJSON.optString("id"),
                    resJSON.optString("username"),
                    resJSON.optInt("media_count"))*/

                Log.d(TAG, resJSON.toString())
                getUserData(token, igPageId)

            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }

    /*

     */
    private fun getFbUserPages(token: String)
    {
        val TAG = "getFbUserPages"
        val url = "https://graph.facebook.com/v7.0/me/accounts" +
                "?access_token=" + token

        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)
                val userPagesData = resJSON.optJSONObject("data")
                val userFbPageId = userPagesData.optString("id")

                /*sendUserInfo(resJSON.optString("id"),
                    resJSON.optString("username"),
                    resJSON.optInt("media_count"))*/

                Log.d(TAG, resJSON.toString())

                getInstagramPageId(token, userFbPageId)

            }, Response.ErrorListener { error ->  Log.d(TAG, error.toString())})

        queue.add(stringRequest)
    }

    /*
    metricsPeriod :"day","week","days_28"
     */
    private fun getAccountMetrics(token: String, igUserId: String, metricsPeriod: String)
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


        getHttpRequest(url, TAG)
    }
}