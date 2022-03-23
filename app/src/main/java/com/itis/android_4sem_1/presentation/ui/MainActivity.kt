package com.itis.android_4sem_1.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.presentation.ui.SearchCityFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SearchCityFragment.newInstance())
            .commit()
    }
}
