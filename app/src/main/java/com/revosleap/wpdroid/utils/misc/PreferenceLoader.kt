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