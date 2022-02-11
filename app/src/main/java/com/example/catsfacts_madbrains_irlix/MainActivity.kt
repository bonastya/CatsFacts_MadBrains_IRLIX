package com.example.catsfacts_madbrains_irlix

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.catsfacts_madbrains_irlix.ui.main.SectionsPagerAdapter
import com.example.catsfacts_madbrains_irlix.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRealm()


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab
        tabs.getTabAt(0)?.setIcon(R.drawable.ic_baseline_search_24);
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_baseline_favorite_24);


        fab.setOnClickListener { view ->
            try {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("mailto:bonastya@gmail.com?subject=Тестовое задание по курсу Аndroid"))
                ContextCompat.startActivity(this, browserIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Проверьте интернет соединение", Toast.LENGTH_LONG)
                    .show()
                e.printStackTrace()
            }
        }
    }

    private fun initRealm(){
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}