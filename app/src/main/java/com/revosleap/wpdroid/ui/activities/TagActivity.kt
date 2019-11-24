package com.revosleap.wpdroid.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.fragments.FragmentTagList
import com.revosleap.wpdroid.utils.misc.Themer
import kotlinx.android.synthetic.main.activity_tags.*

class TagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer(this).setTheme()
        setContentView(R.layout.activity_tags)
        setSupportActionBar(toolbarTags)
        loadTags()
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun loadTags() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.tagView, FragmentTagList())
            .commit()
    }
}