package io.symbyoz.testbusinessig.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import io.symbyoz.testbusinessig.R
import io.symbyoz.testbusinessig.webservice.IGBusinessAPI


class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: LoginButton
    private lateinit var callbackManager: CallbackManager
    private lateinit var igBusiness: IGBusinessAPI

    private lateinit var btnUserData: Button
    private lateinit var btnUserMetrics: Button
    private lateinit var btnAllMediaMetrics: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBusinessIgSDK()

        initLoginButton()

        initButtons()

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    //
    // INIT METHODS
    // ============================================================================================

    private fun initLoginButton()
    {
        loginButton = findViewById(R.id.login_button)
        loginButton.setReadPermissions("public_profile", "instagram_basic",
            "instagram_manage_comments","email","pages_read_engagement",
            "pages_show_list","instagram_manage_insights")

        callbackManager = CallbackManager.Factory.create()

        loginButton.registerCallback(callbackManager, igBusiness.OnFacebookCallback())
    }

    private fun initBusinessIgSDK()
    {
        val accessToken = AccessToken.getCurrentAccessToken()

        igBusiness = IGBusinessAPI(this)
        if(accessToken != null)
        {
            igBusiness.initWithToken(accessToken.token)
        }
    }

    private fun initButtons()
    {
        btnUserData = findViewById(R.id.user_data_button)
        btnUserMetrics = findViewById(R.id.user_metrics_button)
        btnAllMediaMetrics = findViewById(R.id.all_media_metrics_button)

        btnUserData.setOnClickListener {btnUserDataOnClickListener()}
        btnUserMetrics.setOnClickListener {btnUserMetricsOnClickListener()}
        btnAllMediaMetrics.setOnClickListener { btnSendAllMediaMetricsOnClickListener() }
    }

    //
    // BUTTONS LISTENERS
    // ============================================================================================
    private fun btnUserDataOnClickListener()
    {
        if(igBusiness.isReady)
        {
            igBusiness.getUserData()
        } else
        {
            Toast.makeText(this, "Business API not ready", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnUserMetricsOnClickListener()
    {
        if(igBusiness.isReady)
        {
            igBusiness.getAllUserMetrics()
        } else
        {
            Toast.makeText(this, "BusinessAPI not ready", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnSendAllMediaMetricsOnClickListener()
    {
        if(igBusiness.isReady)
        {
            igBusiness.getAllMediaMetrics()
        } else
        {
            Toast.makeText(this, "BusinessAPI not ready", Toast.LENGTH_SHORT).show()
        }
    }

}