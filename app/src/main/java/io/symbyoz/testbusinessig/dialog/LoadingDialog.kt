package io.symbyoz.testbusinessig.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import io.symbyoz.testbusinessig.R

class LoadingDialog : DialogFragment()
{
    val TAG = "LoadingDialog"

    private var textPercent: TextView? = null

    // Interaction listener
    private var mListener: OnLoadingInteractionListener? = null



    //
    // FRAGMENT LIFE CYCLE
    // =========================================================================================
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        // Create the Alert Dialog builder
        val builder = AlertDialog.Builder(requireActivity())

        // Inflate the custom view
        val view: View = requireActivity().layoutInflater.inflate(R.layout.dialog_loading_fragment, null)
        textPercent = view.findViewById(R.id.loading_fragment_progress_text)

        // Return the dialog fragment
        return builder.setView(view).create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // No white background
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)

        return super.onCreateView(inflater, container, savedInstanceState)
    }



    //
    // ATTACH / DETACH LISTENER
    // =========================================================================================

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        mListener = if (context is OnLoadingInteractionListener)
        {
            context
        }
        else
        {
            throw RuntimeException("$context must implement OnLoadingInteractionListener")
        }
    }

    override fun onDetach()
    {
        super.onDetach()
        mListener = null
    }



    //
    // PROGRESS (IF YOU NEED TO SHOW A PROGRESSION)
    // =========================================================================================

    fun updateProgress(message: String?, percentage: Int)
    {
        if (textPercent!!.visibility != View.VISIBLE)
        {
            textPercent!!.visibility = View.VISIBLE
        }
        if (percentage > 100 || percentage < 0)
        {
            if(!message.isNullOrEmpty())
            {
                textPercent!!.text = message
            }
        }
        else
        {
            if(!message.isNullOrEmpty())
            {
                textPercent!!.text = "$message $percentage%"
            }
            else
            {
                textPercent!!.text = "$percentage%"
            }
        }
    }


    //
    // INTERFACE DELEGATE (LISTENER)
    // =========================================================================================

    interface OnLoadingInteractionListener


}

