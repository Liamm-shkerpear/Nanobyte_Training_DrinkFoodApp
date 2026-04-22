package com.example.drinkfoodapp.main.ui.mainscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ActivityMainBinding
import com.example.drinkfoodapp.main.models.DrinkItem
import com.example.drinkfoodapp.main.models.FoodItem
import com.example.drinkfoodapp.main.ui.mainscreen.activity.DetailActivity
import com.example.drinkfoodapp.main.ui.mainscreen.adapter.ViewPagerAdapter
import com.example.drinkfoodapp.main.ui.mainscreen.dialog.AddItemBottomSheet
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar icons to dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setUpView()
        observeViewModel()
    }

    private fun setUpView() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(this@MainActivity)

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
                    bottomNav.menu[menuIndex].isChecked = true
                    viewModel.clearSelection()
                }
            })

            fabCreate.setOnClickListener {
                val isDrinkSelected = binding.viewPager.currentItem == TAB_DRINK_POSITION
                val bottomSheet = AddItemBottomSheet.newInstance(isDrinkSelected)
                bottomSheet.show(supportFragmentManager, "AddBottomSheet")
            }

            bottomNav.menu[1].isEnabled = false

        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModel.selectedItem.observe(this) { item ->
            if (item == null) {
                binding.tvPrice.visibility = View.GONE
                binding.btnViewDetail.setOnClickListener {
                    Toast.makeText(this, "Vui lòng chọn món ăn", Toast.LENGTH_SHORT).show()
                }
            }else {
                val price = when (item) {
                    is DrinkItem -> item.price
                    is FoodItem -> item.price
                    else -> 0
                }
                binding.tvPrice.text = "${price}đ"
                binding.tvPrice.visibility = View.VISIBLE
                binding.btnViewDetail.setOnClickListener {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)

                    when (item) {
                        is DrinkItem -> {
                            intent.putExtra("EXTRA_DRINK", item)
                            intent.putExtra("IS_DRINK", true)
                        }
                        is FoodItem -> {
                            intent.putExtra("EXTRA_FOOD", item)
                            intent.putExtra("IS_DRINK", false)
                        }
                    }
                    startActivity(intent)
                }
            }
        }
    }


    companion object {
        private const val TAB_DRINK_POSITION = 0
        private const val TAB_FOOD_POSITION = 2
    }

}
