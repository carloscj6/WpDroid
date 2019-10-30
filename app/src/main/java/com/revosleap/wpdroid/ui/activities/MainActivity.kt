package com.revosleap.wpdroid.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.dialogs.BottomSheetTags
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewBlog
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewCategory
import com.revosleap.wpdroid.ui.recyclerview.models.category.CategoryResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.callbacks.CategorySelection
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),
    AnkoLogger, CategorySelection {
    private var selectCategoryId: Long? = null
    private val wpDroidAdapter = WpDroidAdapter()
    private val categoryAdapter = WpDroidAdapter()
    private var toggle: ActionBarDrawerToggle? = null
    var wpDataService: GetWpDataService? = null
    val itemViewCategory = ItemViewCategory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        wpDroidAdapter.register(ItemViewBlog())
        categoryAdapter.register(itemViewCategory)
        wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        loadUI()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_tags -> {
                BottomSheetTags().show(supportFragmentManager, "Tags")
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun loadUI() {
        itemViewCategory.setCategorySelection(this)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_LONG)
                .setAction("Stop", null).show()
        }

        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open
            , R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle!!)
        toggle?.syncState()
        instantiateCategoryRecyclerView()
        instantiateRecyclerView()
        getPosts(1, null)
        getCategories(1)

    }

    override fun onCategorySelected(categoryId: Long) {
        drawer_layout.closeDrawer(GravityCompat.START)
        wpDroidAdapter.clearItems()
        getPosts(1, categoryId)

    }

    private fun instantiateRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewPosts.apply {
            adapter = wpDroidAdapter
            layoutManager = linearLayoutManager
            hasFixedSize()
            addOnScrollListener(object : RecyclerViewPagination(linearLayoutManager) {
                override fun onLoadMore(
                    page: Long,
                    totalItemsCount: Int,
                    view: RecyclerView
                ) {
                    if (selectCategoryId != null) {
                        getPosts(page, selectCategoryId)
                    } else getPosts(page, null)
                }
            })
        }

    }


    private fun getPosts(page: Long, categoryId: Long?) {
        if (page == 1L) {
            updateUi(Utilities.LOADING)
        }
        val call: Call<List<PostResponse>>? = if (categoryId == null) {
            selectCategoryId = null
            wpDataService?.getWpPosts(20, page)
        } else {
            selectCategoryId = categoryId
            wpDataService?.getWpPostsCategorized(categoryId, 20, page)
        }

        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                warn("${call.request().url()} \n ${t.message}")
                updateUi(Utilities.ERROR)
            }

            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                warn(call.request().url().toString())
                if (response.isSuccessful) {
                    if (response.body()?.size != null && response.body()?.size!! > 0) {
                        linearLayoutOops?.visibility = View.GONE
                        recyclerViewPosts?.visibility = View.VISIBLE
                        wpDroidAdapter.addItems(response.body()!!)
                        updateUi(Utilities.SUCCESS)

                    } else {
                        if (page == 1L) {
                            updateUi(Utilities.ERROR)
                            textViewOops.text = "Request is Successful but no post was found!!"
                        }

                    }
                } else {
                    if (page == 1L) {
                        textViewOops.text =
                            "Request is Successful but there seems to be problem with getting posts"
                        updateUi(Utilities.ERROR)
                        buttonRetry.visibility= View.GONE
                    }

                }


            }
        })
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
        val call = wpDataService?.getWpCategories(30, page, true)
        call?.enqueue(object : Callback<List<CategoryResponse>> {
            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                if (response.body()?.size!! > 0) {
                    progressBarCategories.visibility = View.GONE
                    recyclerViewCategories.visibility = View.VISIBLE
                    categoryAdapter.addItems(response.body()!!)

                }

            }
        })
    }

    private fun updateUi(status: String) {
        when (status) {
            Utilities.LOADING -> {
                progressLoading.visibility = View.VISIBLE
                linearLayoutOops.visibility = View.GONE
                recyclerViewPosts.visibility = View.GONE
            }
            Utilities.SUCCESS -> {
                linearLayoutOops.visibility = View.GONE
                recyclerViewPosts.visibility = View.VISIBLE
                progressLoading.visibility = View.GONE
            }
            Utilities.ERROR -> {
                linearLayoutOops.visibility = View.VISIBLE
                recyclerViewPosts.visibility = View.GONE
                progressLoading.visibility = View.GONE
            }
        }
        buttonRetry?.setOnClickListener {
            getCategories(1)
            getPosts(1,null)
        }
    }
}
