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

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.revosleap.wpdroid.WpDroid
import kotlin.math.roundToInt

object UtilFun {


    fun blurred(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        var blurry: Bitmap? = null
        var img = bitmap
        try {
            img = colorEncodingChange(img)
            blurry = Bitmap.createBitmap(
                img.width, img.height,
                Bitmap.Config.ARGB_8888
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val renderScript = RenderScript.create(context)
        val blurInput = Allocation.createFromBitmap(renderScript, img)
        val blurOutput = Allocation.createFromBitmap(renderScript, blurry)
        val blur = ScriptIntrinsicBlur.create(
            renderScript,
            Element.U8_4(renderScript)
        )
        blur.setInput(blurInput)
        blur.setRadius(radius.toFloat())
        blur.forEach(blurOutput)
        blurOutput.copyTo(img)
        renderScript.destroy()
        return img
    }

    @Throws(Exception::class)
    private fun colorEncodingChange(img: Bitmap): Bitmap {
        val pixelNum = img.width * img.height
        val pixels = IntArray(pixelNum)
        img.getPixels(pixels, 0, img.width, 0, 0, img.width, img.height)
        val result = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)
        result.setPixels(pixels, 0, result.width, 0, 0, result.width, result.height)
        return result
    }

    fun getTextSize():Float{
        val defaultSize= 12
        val scaling= PreferenceLoader(WpDroid.context!!).textScaling
        return (defaultSize*scaling*0.4).toFloat()
    }

    fun getUrlBaseUrl():String{
        val url:String
        val siteUrl= PreferenceLoader(WpDroid.context!!).url
        val encrypt= PreferenceLoader(WpDroid.context!!).useSSl
        val customSite= PreferenceLoader(WpDroid.context!!).inputedSite
        val allowCustomSite= PreferenceLoader(WpDroid.context!!).allowCustomSite
        url = if (allowCustomSite){
            customSite!!
        }else siteUrl!!
        return if (encrypt){
            "https://${url}/wp-json/wp/v2/"
        }else "http://${url}/wp-json/wp/v2/"
    }
}