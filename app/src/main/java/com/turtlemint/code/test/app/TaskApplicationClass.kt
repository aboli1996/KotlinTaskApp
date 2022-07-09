package com.turtlemint.code.test.app

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import org.acra.ACRA
import org.acra.ReportingInteractionMode
import org.acra.annotation.ReportsCrashes

@ReportsCrashes(mailTo = "abolijadhav.10@gmail.com", mode = ReportingInteractionMode.TOAST,resToastText = R.string.str_send_mail_to_dev, formKey = "")
class TaskApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        ACRA.init(this)
    }
}