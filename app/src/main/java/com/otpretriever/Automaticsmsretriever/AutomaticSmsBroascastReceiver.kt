package com.otpretriever.Automaticsmsretriever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
class AutomaticSmsBroascastReceiver(private val otpReceiver: OTPReceiver?) : BroadcastReceiver() {


    companion object {
       const val OTP_LENGTH = 5
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            extras?.let {
                val status = extras.get(SmsRetriever.EXTRA_STATUS) as Status?
                when (status?.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String?
                        message?.let {
                            val otp = message.substring(0, OTP_LENGTH)
                            otpReceiver?.let { it.onOTPReceived(otp) }
                        }

                    }
                    CommonStatusCodes.TIMEOUT -> {
                        otpReceiver?.let { receiver -> receiver.onOTPTimeOut() }
                    }
                    else -> {
                    }
                }// Extract one-time code from the message and complete verification
            }
        }

    }

    interface OTPReceiver {
        fun onOTPReceived(otp: String)
        fun onOTPTimeOut() {}
    }
}