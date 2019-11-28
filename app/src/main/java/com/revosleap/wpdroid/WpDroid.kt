package com.revosleap.wpdroid

import android.app.Application
import android.content.Context
import com.revosleap.wpdroid.utils.misc.ObjectBox
import com.revosleap.wpdroid.utils.misc.Themer
import com.revosleap.wpdroid.utils.misc.Websites

class WpDroid : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Themer(this).setTheme()
        ObjectBox.init(this)

    }


    companion object {
        var instance: WpDroid? = null
            private set

        val context: Context?
            get() = instance
    }
}