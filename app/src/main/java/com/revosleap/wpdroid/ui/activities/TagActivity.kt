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

package com.revosleap.wpdroid.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.fragments.FragmentItems
import com.revosleap.wpdroid.ui.fragments.FragmentTagList
import com.revosleap.wpdroid.utils.misc.Themer
import com.revosleap.wpdroid.utils.misc.Utilities
import kotlinx.android.synthetic.main.activity_tags.*

class TagActivity : AppCompatActivity() {
    var id: Long? = null
    var itemType: String? = null
    var title: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer(this).setTheme()
        setContentView(R.layout.activity_tags)
        setSupportActionBar(toolbarTags)
        loadTags()
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        id = intent.getLongExtra(Utilities.ITEM_ID_BUNDLE, -0)
        itemType = intent.getStringExtra(Utilities.ITEM_TYPE_BUNDLE)
        title = intent.getStringExtra(Utilities.ITEM_TITLE_BUNDLE)
        if (id == null || id == -0L) {
            loadTags()

        } else loadTypes()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun loadTags() {
        supportActionBar?.title = "Tags"
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.tagView, FragmentTagList())
            .commit()
    }

    private fun loadTypes() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.tagView, FragmentItems.getInstance(id!!, itemType!!, title!!))
            .commit()
    }


}