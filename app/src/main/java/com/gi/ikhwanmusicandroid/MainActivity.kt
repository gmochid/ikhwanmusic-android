package com.gi.ikhwanmusicandroid

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.firebase.client.Firebase
import com.gi.ikhwanmusicandroid.actions.Dispatcher
import com.gi.ikhwanmusicandroid.actions.PlayerAction
import com.gi.ikhwanmusicandroid.actions.SongAction
import com.gi.ikhwanmusicandroid.stores.PlayerStore
import com.gi.ikhwanmusicandroid.stores.SongStore
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import layout.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var homeFragment: HomeFragment
    lateinit var playFragment: PlayFragment
    lateinit var radioFragment: RadioFragment
    lateinit var settingsFragment: SettingsFragment
    lateinit var searchResultFragment: SearchResultFragment
    lateinit var dispatcher: Dispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        librarySetup()
        fragmentSetup()

        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.content_main, homeFragment).commit()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                SongAction.getInstance(dispatcher).search(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean = true
        })

        searchView.setOnQueryTextFocusChangeListener { view, b ->
            supportFragmentManager.beginTransaction().replace(R.id.content_main, homeFragment).commit()
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        moveToPage(id)

        return true
    }

    /**
     * Add library initialization setup here
     */
    fun librarySetup() {
        Firebase.setAndroidContext(this)
    }

    /**
     * Add fragment initialization setup here
     */
    fun fragmentSetup() {
        dispatcher = Dispatcher.get(Bus())
        val playerStore = PlayerStore.getInstance(dispatcher)
        val songStore = SongStore.getInstance(dispatcher)

        dispatcher.register(this)
        dispatcher.register(playerStore)
        dispatcher.register(songStore)

        val playerAction = PlayerAction.getInstance(dispatcher)

        homeFragment = HomeFragment.newInstance(songStore, playerStore, playerAction, dispatcher)
        playFragment = PlayFragment.newInstance(playerStore, playerAction, dispatcher)
        radioFragment = RadioFragment.newInstance(playerStore, playerAction, dispatcher)
        settingsFragment = SettingsFragment.newInstance()
        searchResultFragment = SearchResultFragment.newInstance(songStore, playerStore, playerAction, dispatcher)
    }

    fun moveToPage(itemId: Int) {
        when (itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, homeFragment).commit()
            }
            R.id.nav_play -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, playFragment).commit()
            }
            R.id.nav_radio -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, radioFragment).commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, settingsFragment).commit()
            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return
    }

    @Subscribe
    fun onSongStoreSearchChangeEvent(event: SongStore.SongStoreSearchChangeEvent) {
        supportFragmentManager.beginTransaction().replace(R.id.content_main, searchResultFragment).commit()
    }
}
