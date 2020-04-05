package com.otpretriever.Automaticsmsretriever

import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.otpretriever.R
import com.otpretriever.base.BaseActivity
import com.otpretriever.util.AppSignatureHelper
import kotlinx.android.synthetic.main.activity_automatic_sms_retriever.*


class AutomaticSMSRetrieverActivity : BaseActivity(), AutomaticSmsBroascastReceiver.OTPReceiver {


    companion object {
        val TAG = AutomaticSMSRetrieverActivity.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automatic_sms_retriever)
        registerBroasdcastReceiver(AutomaticSmsBroascastReceiver(this))
        val hash = AppSignatureHelper(this).appSignatures[0]
        tvGuideline.text = String.format(
            resources.getString(R.string.send_sms),
            hash, hash
        )

        startListeningToIncomingMessage()

    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }

    }

    private fun startListeningToIncomingMessage() {

        showProgressBar(true)
        // Get an instance of SmsRetrieverClient, used to start listening for a matching
        // SMS message.

        val task = SmsRetriever.getClient(this@AutomaticSMSRetrieverActivity).startSmsRetriever()
        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnCompleteListener { task ->
            // Successfully started retriever, expect broadcast intent
            if (task.isSuccessful) {
                showToast("Client Started Successfully")
                //Send your OTP generation request to server here
                showProgressBar(false)
            } else {
                showToast("$TAG ${task.exception?.message}")
                showProgressBar(false)
            }
        }

    }

    override fun onOTPReceived(otp: String) {
        edtSms.setText(otp)
    }
}