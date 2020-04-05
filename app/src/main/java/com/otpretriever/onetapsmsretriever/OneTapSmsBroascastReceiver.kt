package com.otpretriever.onetapsmsretriever

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.otpretriever.base.BaseActivity
import com.otpretriever.onetapsmsretriever.OneTapSmsRetrieverActivity.Companion.SMS_CONSENT_REQUEST


/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
class OneTapSmsBroascastReceiver(private val activity: BaseActivity) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            extras?.let {
                val status = extras.get(SmsRetriever.EXTRA_STATUS) as Status?
                when (status?.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get consent intent
                        val consentIntent =
                            extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            consentIntent?.let {
                                startActivityForResult(
                                    activity,
                                    consentIntent,
                                    SMS_CONSENT_REQUEST,
                                    null
                                )
                            }
                        } catch (e: ActivityNotFoundException) {
                            // Handle the exception ...
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                    }
                    else -> {
                    }
                }// Extract one-time code from the message and complete verification
            }
        }

    }
}