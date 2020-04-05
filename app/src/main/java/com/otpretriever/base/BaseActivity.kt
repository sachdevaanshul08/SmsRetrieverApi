package com.otpretriever.base

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever

open class BaseActivity : AppCompatActivity() {

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    protected fun <T> moveToNextActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    protected fun registerBroasdcastReceiver(receiver: BroadcastReceiver) {
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        intentFilter.addAction(SmsRetriever.SEND_PERMISSION)
        applicationContext.registerReceiver(receiver, intentFilter)
    }

}