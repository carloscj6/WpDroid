package com.revosleap.wpdroid

import android.app.Application
import android.content.SharedPreferences
import androidx.core.app.ActivityCompat.recreate
import androidx.preference.PreferenceManager
import com.revosleap.wpdroid.ui.activities.SettingsActivity
import com.revosleap.wpdroid.utils.misc.PreferenceLoader
import com.revosleap.wpdroid.utils.misc.Themer

class WpDroid :Application(){
    override fun onCreate() {
        super.onCreate()
        PreferenceLoader(this)
        Themer(this).setTheme()

    }


}