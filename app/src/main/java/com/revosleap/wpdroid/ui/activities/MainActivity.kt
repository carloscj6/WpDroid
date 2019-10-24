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
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.PostsAdapter
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewBlog
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewCategory
import com.revosleap.wpdroid.ui.recyclerview.models.category.CategoryResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
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

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    AnkoLogger {

    private val wpDroidAdapter = WpDroidAdapter()
    private val categoryAdapter = WpDroidAdapter()
    private var toggle: ActionBarDrawerToggle? = null
    var wpDataService: GetWpDataService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        wpDroidAdapter.register(ItemViewBlog())
        categoryAdapter.register(ItemViewCategory())
        wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        loadUI()
//        instRecyclerView(recyclerViewCategories, CategoryResponse())
//        instRecyclerView(recyclerViewPosts, PostResponse())

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

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        return true
    }

    private fun loadUI() {
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
        nav_view.setNavigationItemSelectedListener(this)

        instantiateCategoryRecyclerView()
        instantiateRecyclerView()
        getPosts(1)
        getCategories(1)

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
                    getPosts(page)
                }
            })
        }

    }

    private fun getPosts(page: Long) {

        val postsAdapter = PostsAdapter()
        val wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        val call = wpDataService?.getWpPosts(30, page)
        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                if (response.body()?.size!! > 0) {
                    linearLayoutOops.visibility = View.GONE
                    recyclerViewPosts.visibility = View.VISIBLE
                    wpDroidAdapter.addItems(response.body()!!)

                } else textViewOops.text = response.errorBody()?.string()

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
        val call = wpDataService?.getWpCategories(30, page)
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


    private fun getData(call: Call<List<Any>>, dataClass: Any) {
        warn("Getting data")
        call.enqueue(object : Callback<List<Any>> {
            override fun onFailure(call: Call<List<Any>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Any>>, response: Response<List<Any>>) {
                updateUi(dataClass)
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items!!.isNotEmpty()) {
                        addItemsToRecyclerView(listOf(items), dataClass)
                    }
                }
            }
        })
    }

    private fun addItemsToRecyclerView(itemList: List<Any>, dataClass: Any) {

        when (dataClass) {
            PostResponse() -> {

                val items= itemList as List<PostResponse>
                wpDroidAdapter.addItems(items)
                warn("Adding posts to RC ${items.size}")
            }
            CategoryResponse() -> {
                val items= itemList as List<CategoryResponse>
                categoryAdapter.addItems(items)
                warn("Adding categories to RC ${items.size}")
            }
        }
    }

    private fun instRecyclerView(recyclerView: RecyclerView, dataClass: Any) {
        warn("Inst Recyclerview")
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        when (dataClass) {
            CategoryResponse() ->{
                recyclerView.adapter = categoryAdapter
                callCategories(1)
            }
            PostResponse() -> {
                recyclerView.adapter = wpDroidAdapter
                callPosts(1)
            }
        }
        recyclerView.apply {
            adapter = categoryAdapter
            layoutManager = linearLayoutManager
            hasFixedSize()
            addOnScrollListener(object : RecyclerViewPagination(linearLayoutManager) {
                override fun onLoadMore(
                    page: Long,
                    totalItemsCount: Int,
                    view: RecyclerView
                ) {
                    when (dataClass) {
                        CategoryResponse() -> callCategories(page)
                        PostResponse() -> callPosts(page)
                    }
                }
            })
        }
    }

    private fun callCategories(page: Long) {
        warn("Calling categories")
        val call = wpDataService?.getWpCategories(30, page)!!
        getData(call as Call<List<Any>>, CategoryResponse())
    }

    private fun callPosts(page: Long) {
        warn("Calling posts")
        val call = wpDataService?.getWpPosts(30, page)!!
        getData(call as Call<List<Any>>, PostResponse())
    }

    private fun updateUi(dataClass: Any) {
        warn("Updating ui")
        when (dataClass) {
            CategoryResponse() -> {
                progressBarCategories.visibility = View.GONE
                recyclerViewCategories.visibility = View.VISIBLE
            }
            PostResponse() -> {
                linearLayoutOops.visibility = View.GONE
                recyclerViewPosts.visibility = View.VISIBLE
            }
        }
    }
}
