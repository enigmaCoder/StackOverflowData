package com.ascensia.stackoverflowdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ascensia.stackoverflowdata.ui.main.MainFragment
import com.ascensia.stackoverflowdata.ui.main.SearchFragment
import com.ascensia.stackoverflowdata.ui.main.SettingsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance()).replace(R.id.filterOptionsFrame,SettingsFragment.newInstance()).replace(R.id.searchViewFrame,SearchFragment.newInstance())
                    .commitNow()
    }
}