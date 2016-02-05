package com.gi.ikhwanmusicandroid

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.firebase.client.Firebase
import com.gi.ikhwanmusicandroid.actions.Dispatcher
import com.gi.ikhwanmusicandroid.actions.PlayerAction
import com.gi.ikhwanmusicandroid.models.Song
import com.gi.ikhwanmusicandroid.stores.PlayerStore
import com.gi.ikhwanmusicandroid.stores.SongStore
import com.squareup.otto.Bus
import io.fabric.sdk.android.Fabric
import layout.HomeFragment
import layout.PlayFragment
import layout.RadioFragment
import layout.SettingsFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var homeFragment: HomeFragment
    lateinit var playFragment: PlayFragment
    lateinit var radioFragment: RadioFragment
    lateinit var settingsFragment: SettingsFragment
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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

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
        val songStore = SongStore.getInstance(dispatcher, Firebase(BuildConfig.FIREBASE_URL))

        dispatcher.register(this)
        dispatcher.register(playerStore)
        dispatcher.register(songStore)

        val playerAction = PlayerAction.getInstance(dispatcher)

        homeFragment = HomeFragment.newInstance(songStore, dispatcher)
        playFragment = PlayFragment.newInstance(playerStore, playerAction, dispatcher)
        radioFragment = RadioFragment.newInstance(playerStore, playerAction, dispatcher)
        settingsFragment = SettingsFragment.newInstance()
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

    fun playSong(song: Song) {
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.menu.getItem(1).setChecked(true)
    }
}
