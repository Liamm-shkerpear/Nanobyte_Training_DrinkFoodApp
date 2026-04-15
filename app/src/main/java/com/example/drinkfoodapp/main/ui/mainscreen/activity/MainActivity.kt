package com.example.drinkfoodapp.main.ui.mainscreen.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.example.drinkfoodapp.databinding.ActivityMainBinding
import com.example.drinkfoodapp.main.ui.mainscreen.adapter.ViewPagerAdapter
import com.example.drinkfoodapp.main.ui.mainscreen.MainViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.main.ui.mainscreen.adapter.AddItemBottomSheet

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar icons to dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            val adapter = ViewPagerAdapter(this@MainActivity)
            viewPager.adapter = adapter

            bottomNav.setOnItemSelectedListener { item ->
                val position = when (item.itemId) {
                    R.id.nav_drink -> TAB_DRINK_POSITION
                    R.id.nav_food -> TAB_FOOD_POSITION
                    else -> return@setOnItemSelectedListener false
                }
                viewPager.setCurrentItem(position, true)
                true
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val menuIndex = if (position == TAB_DRINK_POSITION) 0 else 2
                    bottomNav.menu.getItem(menuIndex).isChecked = true

                }
            })

            fabCreate.setOnClickListener {
               val bottomSheet = AddItemBottomSheet()
                bottomSheet.show(supportFragmentManager, "AddItemBottomSheet")
            }

            bottomNav.menu.getItem(1).isEnabled = false

        }
    }


    companion object {
        private const val TAB_DRINK_POSITION = 0
        private const val TAB_FOOD_POSITION = 2
    }

}
