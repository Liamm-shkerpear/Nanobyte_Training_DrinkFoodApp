package com.example.drinkfoodapp.main.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.example.drinkfoodapp.databinding.ActivityDetailBinding
import com.example.drinkfoodapp.main.models.DrinkItem
import com.example.drinkfoodapp.main.models.FoodItem

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        loadData()
        backToMain()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        val isDrink = intent.getBooleanExtra("IS_DRINK", true)

        if (isDrink) {
            val drinkItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("EXTRA_DRINK", DrinkItem::class.java)
            } else {
                intent.getParcelableExtra("EXTRA_DRINK")
            }
            drinkItem?.run {
                binding.ivDetailImage.setImageResource(imageResId)
                binding.tvDetailName.text = name
                binding.tvDetailPrice.text = "${price}đ"
                binding.tvDetailDesc.text = description
            } ?: showError()
        } else {
            val foodItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("EXTRA_FOOD", FoodItem::class.java)
            } else {
                intent.getParcelableExtra("EXTRA_FOOD")
            }
            foodItem?.run {
                binding.ivDetailImage.setImageResource(imageResId)
                binding.tvDetailName.text = name
                binding.tvDetailPrice.text = "${price}đ"
                binding.tvDetailDesc.text = description
            } ?: showError()
        }
    }

    private fun showError() {
        Toast.makeText(this, "Không thể tải thông tin món ăn!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun backToMain() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
