/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.utils.misc

import android.content.Context
import android.preference.PreferenceManager
import com.revosleap.wpdroid.R

class PreferenceLoader(context: Context) {
 private  val manager= PreferenceManager.getDefaultSharedPreferences(context)
    val postLimit= manager.getInt(context.getString(R.string.post_limit),10)
    val commentLimit= manager.getInt(context.getString(R.string.comment_limit),5)
    val tagLimit= manager.getInt(context.getString(R.string.tag_limit),30)
    val blurRadius= manager.getInt(context.getString(R.string.picture_blur),20)
    val themeColor= manager.getInt(context.getString(R.string.theme_color),0)
    val textScaling= manager.getInt(context.getString(R.string.text_scaling),1)
    val lineSpacing= manager.getInt(context.getString(R.string.line_spacing),1)
    val url= manager.getString(context.getString(R.string.app_sites),"tecmint.com")
    val useSSl= manager.getBoolean(context.getString(R.string.use_ssl),true)
    val inputedSite= manager.getString(context.getString(R.string.input_site),url)
    val allowCustomSite= manager.getBoolean(context.getString(R.string.use_custom_site),false)
}