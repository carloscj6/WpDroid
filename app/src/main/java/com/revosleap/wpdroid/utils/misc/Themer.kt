/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 *  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.utils.misc

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.revosleap.wpdroid.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn

class Themer(private val application: Application?,private val appCompatActivity: AppCompatActivity?):AnkoLogger {
    constructor( appCompatActivity: AppCompatActivity):this(null,appCompatActivity  )
    constructor(application: Application):this(application,null)

    val context:Context= getCurrentContext()!!

    fun setTheme() {
        var selectedColor=0
        val selectedTheme = PreferenceLoader(context).themeColor
        if (selectedTheme!=0){
           selectedColor = selectedTheme
        }

        warn(selectedTheme)
        warn(getColor(R.color.redTheme))
        if (selectedTheme == 0 || selectedTheme == getColor(R.color.defaultTheme)) {
            context.setTheme(R.style.AppTheme)
        } else if (selectedColor == getColor(R.color.redTheme)) {
            context.setTheme(R.style.AppTheme_RedTheme)
        } else if (selectedColor == getColor(R.color.pinkTheme)) {
            context.setTheme(R.style.AppTheme_PinkTheme)
        } else if (selectedColor == getColor(R.color.purpleTheme)) {
            context.setTheme(R.style.AppTheme_PurpleTheme)
        } else if (selectedColor == getColor(R.color.deepPurpleTheme)) {
            context.setTheme(R.style.AppTheme_DeepPurpleTheme)
        } else if (selectedColor == getColor(R.color.indigoTheme)) {
            context.setTheme(R.style.AppTheme_IndigoTheme)
        } else if (selectedColor == getColor(R.color.blueTheme)) {
            context.setTheme(R.style.AppTheme_BlueTheme)
        } else if (selectedColor == getColor(R.color.lightBlueTheme)) {
            context.setTheme(R.style.AppTheme_LightBlueTheme)
        } else if (selectedColor == getColor(R.color.cyanTheme)) {
            context.setTheme(R.style.AppTheme_CyanTheme)
        } else if (selectedColor == getColor(R.color.tealTheme)) {
            context.setTheme(R.style.AppTheme_TealTheme)
        } else if (selectedColor == getColor(R.color.greenTheme)) {
            context.setTheme(R.style.AppTheme_GreenTheme)
        } else if (selectedColor == getColor(R.color.lightGreenTheme)) {
            context.setTheme(R.style.AppTheme_LightGreenTheme)
        } else if (selectedColor == getColor(R.color.limeTheme)) {
            context.setTheme(R.style.AppTheme_LimeTheme)
        } else if (selectedColor == getColor(R.color.yellowTheme)) {
            context.setTheme(R.style.AppTheme_YellowTheme)
        } else if (selectedColor == getColor(R.color.amberTheme)) {
            context.setTheme(R.style.AppTheme_AmberTheme)
        } else if (selectedColor == getColor(R.color.orangeTheme)) {
            context.setTheme(R.style.AppTheme_OrangeTheme)
        } else if (selectedColor == getColor(R.color.deepOrangeTheme)) {
            context.setTheme(R.style.AppTheme_DeepOrangeTheme)
        } else if (selectedColor == getColor(R.color.brownTheme)) {
            context.setTheme(R.style.AppTheme_BrownTheme)
        } else if (selectedColor == getColor(R.color.greyTheme)) {
            context.setTheme(R.style.AppTheme_GreyTheme)
        } else if (selectedColor == getColor(R.color.blueGreyTheme)) {
            context.setTheme(R.style.AppTheme_BlueGreyTheme)
        }

    }

    private fun getColor(value: Int): Int {
        return ContextCompat.getColor(context, value)
    }

    private fun getCurrentContext():Context?{
        return appCompatActivity ?: application
    }
}