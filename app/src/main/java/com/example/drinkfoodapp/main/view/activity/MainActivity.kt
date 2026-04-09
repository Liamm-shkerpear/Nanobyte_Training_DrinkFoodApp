package com.example.drinkfoodapp.main.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkfoodapp.databinding.ActivityMainBinding
import com.example.drinkfoodapp.main.view.adapter.ViewPagerAdapter
import com.example.drinkfoodapp.main.viewmodel.MainViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.drinkfoodapp.R

class MainActivity : AppCompatActivity() {

    // Khởi tạo ViewModel
    private val viewModel: MainViewModel by viewModels()

    // Khởi tạo View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Thiết lập Adapter
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // Kết nối BottomNav và ViewPager2
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_drink -> binding.viewPager.currentItem = 0
                R.id.nav_food -> binding.viewPager.currentItem = 1
            }
            true
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })

        // Xử lý sự kiện nút bấm điều hướng
        binding.btnNext.setOnClickListener {
            val isDrinkSelected = binding.viewPager.currentItem == 0
            viewModel.nextItem(isDrinkSelected)
        }

        binding.btnPrevious.setOnClickListener {
            val isDrinkSelected = binding.viewPager.currentItem == 0
            viewModel.previousItem(isDrinkSelected)
        }
    }

    override fun onResume() {
        super.onResume()
        // Kích hoạt random món ăn/đồ uống khi mở app lên
        viewModel.randomizeItems()
    }
}