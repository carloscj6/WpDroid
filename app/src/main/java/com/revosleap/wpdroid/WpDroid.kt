/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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