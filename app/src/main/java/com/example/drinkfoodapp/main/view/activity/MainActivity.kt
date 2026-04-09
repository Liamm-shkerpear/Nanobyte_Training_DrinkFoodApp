package com.example.drinkfoodapp.main.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkfoodapp.databinding.ActivityMainBinding
import com.example.drinkfoodapp.main.view.adapter.ViewPagerAdapter
import com.example.drinkfoodapp.main.viewmodel.MainViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.drinkfoodapp.R

/**
 * Main Activity that hosts the ViewPager and Bottom Navigation for Drink and Food categories.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAB_DRINK_POSITION = 0
        private const val TAB_FOOD_POSITION = 1
    }
    private val viewModel: MainViewModel by viewModels()

    // Khởi tạo View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
    }

    private fun setUpView() {
        // Dùng apply để tránh lặp bidning
        binding.apply {
            // Thiết lập Adapter
            val adapter = ViewPagerAdapter(this@MainActivity)
            viewPager.adapter = adapter

            // Kết nối BottomNav và ViewPager2
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
                    bottomNav.menu.getItem(position).isChecked = true

                    // random khi chuyển tab
                    val isDrinkSelected = position == TAB_DRINK_POSITION
                    viewModel.randomizeSingleItem(isDrinkSelected)
                }
            })

            // Xử lý sự kiện nút bấm điều hướng
            btnNext.setOnClickListener {
                val isDrinkSelected = viewPager.currentItem == TAB_DRINK_POSITION
                viewModel.nextItem(isDrinkSelected)
            }

            btnPrevious.setOnClickListener {
                val isDrinkSelected = viewPager.currentItem == TAB_DRINK_POSITION
                viewModel.previousItem(isDrinkSelected)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Random món ăn/đồ uống khi mở app lên
        viewModel.randomizeItems()
    }
}
