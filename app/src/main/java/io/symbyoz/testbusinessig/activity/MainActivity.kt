package io.symbyoz.testbusinessig.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import io.symbyoz.testbusinessig.R
import io.symbyoz.testbusinessig.webservice.IGBusinessAPI


class MainActivity : SuperActivity() {

    private lateinit var loginButton: LoginButton
    private lateinit var callbackManager: CallbackManager
    private lateinit var igBusiness: IGBusinessAPI

    private lateinit var btnUserData: Button
    private lateinit var btnUserMetrics: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnUserData = findViewById(R.id.user_data_button)
        btnUserMetrics = findViewById(R.id.user_metrics_button)

        loginButton = findViewById(R.id.login_button)
        loginButton.setReadPermissions("public_profile", "instagram_basic",
                                        "instagram_manage_comments","email","pages_read_engagement",
                                        "pages_show_list","instagram_manage_insights")

        callbackManager = CallbackManager.Factory.create()

        igBusiness = IGBusinessAPI(this)

        loginButton.registerCallback(callbackManager, igBusiness.OnFacebookCallback())

        btnUserData.setOnClickListener {btnUserDataOnClickListener()}
        btnUserMetrics.setOnClickListener {btnUserMetricsOnClickListener()}

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun btnUserDataOnClickListener()
    {
        if(igBusiness.isReady)
        {
            igBusiness.getUserData()
        } else
        {
            Toast.makeText(this, "Business API not ready", Toast.LENGTH_SHORT).show()
        }
    }

    fun btnUserMetricsOnClickListener()
    {
        if(igBusiness.isReady)
        {
            igBusiness.getAllUserMetrics()
        } else
        {
            Toast.makeText(this, "BusinessAPI not ready", Toast.LENGTH_SHORT).show()
        }
    }
}