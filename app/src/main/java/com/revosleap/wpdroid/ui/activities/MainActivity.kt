package com.revosleap.wpdroid.ui.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.fragments.FragmentPosts
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewCategory
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewSiteCategory
import com.revosleap.wpdroid.ui.recyclerview.models.category.CategoryResponse
import com.revosleap.wpdroid.utils.callbacks.CategorySelection
import com.revosleap.wpdroid.utils.misc.ObjectBox
import com.revosleap.wpdroid.utils.misc.PreferenceLoader
import com.revosleap.wpdroid.utils.misc.Themer
import com.revosleap.wpdroid.utils.misc.Websites
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.sites_layout.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),
    AnkoLogger, CategorySelection, SharedPreferences.OnSharedPreferenceChangeListener {
    private var selectCategoryId: Long? = null
    private var siteCatAdapter = WpDroidAdapter()
    private val categoryAdapter = WpDroidAdapter()
    private var toggle: ActionBarDrawerToggle? = null
    var wpDataService: GetWpDataService? = null
    val itemViewCategory = ItemViewCategory()
    lateinit var preferenceLoader: PreferenceLoader
    private var currentPostPage: Long = 1L
    private var currentCategoryPage: Long = 1L
    private var behaviour: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer(this).setTheme()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        behaviour = BottomSheetBehavior.from(bottomSheetLayout)
        categoryAdapter.register(itemViewCategory)
        preferenceLoader = PreferenceLoader(this)
        wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        loadUI()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)


    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (behaviour?.state == BottomSheetBehavior.STATE_EXPANDED) {
            behaviour?.state = BottomSheetBehavior.STATE_COLLAPSED
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else
            super.onBackPressed()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_tags -> {
                startActivity<TagActivity>()
            }
            R.id.action_settings -> {
                startActivity<SettingsActivity>()
            }
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun loadUI() {
        itemViewCategory.setCategorySelection(this)
        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open
            , R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle!!)
        toggle?.syncState()
        instantiateCategoryRecyclerView()
        getCategories(1)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout_main, FragmentPosts.getInstance(null, null))
            .commit()
        siteCatAdapter.register(ItemViewSiteCategory())
        recyclerViewSites.apply {
            adapter = siteCatAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            hasFixedSize()
        }
        siteCatAdapter.addItems(ObjectBox.siteCategoryBox.all)

    }

    override fun onCategorySelected(categoryId: Long, title: String) {
        supportFragmentManager.popBackStackImmediate()
        drawer_layout.closeDrawer(GravityCompat.START)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout_main, FragmentPosts.getInstance(categoryId, title))
            .addToBackStack(null)
            .commit()

    }


    private fun instantiateCategoryRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewCategories.apply {
            adapter = categoryAdapter
            layoutManager = linearLayoutManager
            hasFixedSize()
            addOnScrollListener(object : RecyclerViewPagination(linearLayoutManager) {
                override fun onLoadMore(
                    page: Long,
                    totalItemsCount: Int,
                    view: RecyclerView
                ) {
                    getCategories(page)
                }
            })
        }

    }

    private fun getCategories(page: Long) {
        currentCategoryPage = page
        val call = wpDataService?.getWpCategories(preferenceLoader.postLimit, page, true)
        call?.enqueue(object : Callback<List<CategoryResponse>> {
            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                if (response.body() != null && response.body()?.size!! > 0) {
                    progressBarCategories.visibility = View.GONE
                    recyclerViewCategories.visibility = View.VISIBLE
                    categoryAdapter.addItems(response.body()!!)

                }

            }
        })
    }


    fun reload() {
        getCategories(currentCategoryPage)
        //getPosts(currentPostPage, selectCategoryId)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key?.equals(getString(R.string.theme_color))!!) {
            Themer(this).setTheme()
            recreate()
        } else if (key == getString(R.string.app_sites) || key == getString(R.string.input_site)
            || key == getString(R.string.use_custom_site)
        ) {


        }
    }
}
