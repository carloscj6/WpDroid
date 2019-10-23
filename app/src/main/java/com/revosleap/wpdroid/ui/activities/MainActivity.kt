package com.revosleap.wpdroid.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.AnkoLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    AnkoLogger {

    private val wpDroidAdapter = WpDroidAdapter()
    private var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        instantiateRecyclerView()
        getPosts(1)

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

    private fun instantiateRecyclerView(){

        wpDroidAdapter.register(ItemViewBlog())
        val linearLayoutManager= LinearLayoutManager(this@MainActivity)
        recyclerViewPosts.apply {
            adapter = wpDroidAdapter
            layoutManager = linearLayoutManager
            hasFixedSize()
            addOnScrollListener(object :RecyclerViewPagination(linearLayoutManager){
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

    private fun getPosts(page:Long) {

        val postsAdapter = PostsAdapter()
        val wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        val call = wpDataService?.getWpPosts(30,page)
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
                    wpDroidAdapter.addItems(response.body()!!,false)

                } else textViewOops.text = response.errorBody()?.string()

            }
        })
    }

}
