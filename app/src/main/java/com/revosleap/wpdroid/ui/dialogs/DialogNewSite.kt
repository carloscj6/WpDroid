/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.misc.SiteCategory
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website_
import com.revosleap.wpdroid.utils.misc.Websites
import kotlinx.android.synthetic.main.new_site_layout.*
import org.jetbrains.anko.toast

class DialogNewSite : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.new_site_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateViews()
    }

//    override fun onResume() {
//        super.onResume()
//        val params = dialog?.window?.attributes
//        params?.width = dialog?.window?.windowManager?.defaultDisplay?.width?.minus(10)
//        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        dialog?.window?.attributes = params as WindowManager.LayoutParams
//    }

    private fun instantiateViews() {
        var category = Websites.uncategorized

        imageButtonCancel?.setOnClickListener {
            dismiss()
        }
        val items = mutableListOf<String>()
        Websites.siteCategoryBox.all.forEach {
            items.add(it.categoryTitle!!)
        }
        val dataAdapter = ArrayAdapter<String>(
            context!!, android.R.layout.simple_spinner_item,
            items
        )
        spinnerCategory?.adapter = dataAdapter
        spinnerCategory?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item = p0?.getItemAtPosition(p2)
                category = SiteCategory(0, item as String)
            }
        }
        imageButtonCheck?.setOnClickListener {
            if (isWorking()) {
                val website = Website(0, getName()!!, getText()!!)
                website.siteCategory.target = category
                Websites.websitesBox.put(website)
                context?.toast("Site Saved Successfully!!")
            }
        }
    }

    private fun getText(): String? {
        val text = textInputLayoutSite?.editText?.text?.toString()
        return when {
            text.isNullOrEmpty() -> {
                textInputLayoutSite?.error = "Site Must Not Be Empty"
                null
            }
            text.startsWith("http") -> {
                textInputLayoutSite?.error = "Site should not begin with http"
                null
            }
            else -> {
                text.trim()
            }
        }
    }

    private fun getName(): String? {
        val text = textInputLayoutSiteName?.editText?.text?.toString()
        return when {
            text.isNullOrEmpty() -> {
                textInputLayoutSite?.error = "Site Name Must Not Be Empty"
                null
            }

            else -> {
                text.trim()
            }
        }
    }

    private fun isWorking(): Boolean {
        if (isSiteSaved()) {
            context?.toast("Site already Saved")
            return false
        } else return getName() != null && getText() != null
    }

    private fun isSiteSaved(): Boolean {
        val queryBuilder = Websites.websitesBox.query()
        queryBuilder.equal(Website_.url, textInputLayoutSite?.editText?.text?.toString()!!)
        val items = queryBuilder.build().find()
        return items.size > 0
    }
}