package com.otpretriever

import android.os.Bundle
import com.otpretriever.Automaticsmsretriever.AutomaticSMSRetrieverActivity
import com.otpretriever.base.BaseActivity
import com.otpretriever.onetapsmsretriever.OneTapSmsRetrieverActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSmsRetreiver.setOnClickListener {
            moveToNextActivity(AutomaticSMSRetrieverActivity::class.java)
        }
        btnSmsConsent.setOnClickListener {
            moveToNextActivity(OneTapSmsRetrieverActivity::class.java)
        }
    }
}
