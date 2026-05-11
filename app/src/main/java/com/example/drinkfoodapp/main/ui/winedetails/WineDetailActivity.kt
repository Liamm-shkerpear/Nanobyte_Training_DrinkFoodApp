package com.example.drinkfoodapp.main.ui.winedetails

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ActivityWineDetailBinding
import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import com.example.drinkfoodapp.main.utils.AppConstants

class WineDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWineDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWineDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        loadData()
        backToMain()
    }

    private fun loadData() {
        val wineItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(AppConstants.EXTRA_WINE, WineItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(AppConstants.EXTRA_WINE)
        }
        wineItem?.run {
            binding.tvDetailTitle.text = wineItem.wine ?: "Unknow wine"
            binding.tvDetailWinery.text = wineItem.winery ?: "Unknow winery"
            binding.tvDetailLocation.text =
                getString(R.string.originWine, wineItem.location ?: "Unknow location")
            binding.tvDetailAverage.text =
                getString(R.string.rating_star, wineItem.rating?.average ?: "N/A")
            binding.tvDetailReviews.text =
                getString(R.string.wine_reviews, wineItem.rating?.reviews ?: "0 rating")
            Glide.with(this@WineDetailActivity)
                .load(wineItem.image)
                .centerCrop()
                .into(binding.ivDetailImage)
        }
    }

    private fun backToMain() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}
