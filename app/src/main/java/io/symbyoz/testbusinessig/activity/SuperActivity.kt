package io.symbyoz.testbusinessig.activity

import androidx.appcompat.app.AppCompatActivity
import io.symbyoz.testbusinessig.dialog.LoadingDialog

open class SuperActivity: AppCompatActivity(), LoadingDialog.OnLoadingInteractionListener
{
    protected var loadingDialog: LoadingDialog? = null


    // Simple function to call the date picker
    open fun showLoadingScreen()
    {
        // Show loading screen
        loadingDialog = LoadingDialog()
        // Show loading dialog
        loadingDialog!!.show(supportFragmentManager, "loadingDialog")
    }

    // Hide loading screen
    open fun hideLoadingScreen()
    {
        // Double check for some cases
        if (loadingDialog != null)
        {
            try
            {
                loadingDialog!!.dismiss()
            }
            catch (e: Exception)
            {

            }
        }
        loadingDialog = null
    }
}