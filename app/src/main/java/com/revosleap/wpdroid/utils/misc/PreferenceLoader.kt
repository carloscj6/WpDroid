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
}