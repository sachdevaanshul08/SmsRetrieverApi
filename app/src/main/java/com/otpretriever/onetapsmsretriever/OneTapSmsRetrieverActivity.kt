package com.otpretriever.onetapsmsretriever

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.otpretriever.Automaticsmsretriever.AutomaticSMSRetrieverActivity
import com.otpretriever.R
import com.otpretriever.base.BaseActivity
import kotlinx.android.synthetic.main.activity_onetap_sms_retriever.*

class OneTapSmsRetrieverActivity : BaseActivity() {

    companion object {
        const val SMS_CONSENT_REQUEST = 2  // Set to an unused request code
        const val OTP_LENGTH = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onetap_sms_retriever)
        registerBroasdcastReceiver(OneTapSmsBroascastReceiver(this))

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
        val client = SmsRetriever.getClient(this)
        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        val task = client.startSmsUserConsent(null)
        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnCompleteListener { task ->
            // Successfully started retriever, expect broadcast intent
            if (task.isSuccessful) {
                showToast("Client Started Successfully")
                //Send your OTP generation request to server here
                showProgressBar(false)
            } else {
                showToast("${AutomaticSMSRetrieverActivity.TAG} ${task.exception?.message}")
                showProgressBar(false)
            }
        }


    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // ...
            SMS_CONSENT_REQUEST ->
                // Obtain the phone number from the result
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Get SMS message content
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    message?.let {
                        val otp = message.substring(0, OTP_LENGTH)
                        displayOnUI(otp)
                    }
                    // send one time code to the server
                } else {
                    // Consent denied. User can type OTC manually.
                }
        }
    }

    private fun displayOnUI(otp: String) {
        edtSms.setText(otp)
    }
}