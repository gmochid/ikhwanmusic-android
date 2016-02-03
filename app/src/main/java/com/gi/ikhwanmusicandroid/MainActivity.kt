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
import com.firebase.client.Firebase
import com.gi.ikhwanmusicandroid.models.Song
import io.fabric.sdk.android.Fabric
import layout.HomeFragment
import layout.PlayFragment
import layout.RadioFragment
import layout.SettingsFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        librarySetup()

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

        supportFragmentManager.beginTransaction().replace(R.id.content_main, HomeFragment()).commit()
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
        Fabric.with(this, Crashlytics())

        Firebase.setAndroidContext(this)
    }

    fun moveToPage(itemId: Int) {
        when (itemId) {
            R.id.nav_home -> {
                var homeFragment = supportFragmentManager.findFragmentById(R.id.fragment_home)
                homeFragment = if (homeFragment != null) homeFragment else HomeFragment()

                supportFragmentManager.beginTransaction().replace(R.id.content_main, homeFragment).commit()
            }
            R.id.nav_play -> {
                var playFragment = supportFragmentManager.findFragmentById(R.id.fragment_play)
                playFragment = if (playFragment != null) playFragment else PlayFragment()

                supportFragmentManager.beginTransaction().replace(R.id.content_main, playFragment).commit()
            }
            R.id.nav_radio -> {
                var radioFragment = supportFragmentManager.findFragmentById(R.id.fragment_radio)
                radioFragment = if (radioFragment != null) radioFragment else RadioFragment()

                supportFragmentManager.beginTransaction().replace(R.id.content_main, radioFragment).commit()
            }
            R.id.nav_settings -> {
                var settingsFragment = supportFragmentManager.findFragmentById(R.id.fragment_settings)
                settingsFragment = if (settingsFragment != null) settingsFragment else SettingsFragment()

                supportFragmentManager.beginTransaction().replace(R.id.content_main, settingsFragment).commit()
            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }

    fun playSong(song: Song) {
        var playFragment = supportFragmentManager.findFragmentById(R.id.fragment_play)
        if (playFragment != null) {
            (playFragment as PlayFragment).playSong()
        } else {
            playFragment = PlayFragment.newInstance(song, true)
            supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content_main, playFragment).commit()
        }

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.menu.getItem(1).setChecked(true)
    }
}
