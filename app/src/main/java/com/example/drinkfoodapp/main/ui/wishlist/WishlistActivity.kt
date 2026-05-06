package com.example.drinkfoodapp.main.ui.wishlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkfoodapp.databinding.ActivityWishlistBinding
import com.example.drinkfoodapp.main.di.Injection
import com.example.drinkfoodapp.main.di.ViewModelFactory
import com.example.drinkfoodapp.main.ui.drink.adapter.DrinkMenuAdapter
import com.example.drinkfoodapp.main.ui.home.HomeScreenViewModel

class WishlistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishlistBinding
    private val viewModel: HomeScreenViewModel by viewModels {
        ViewModelFactory(Injection.provideMenuRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = DrinkMenuAdapter (
            onFavoriteClick = { item ->
                viewModel.handleFavorite(item)
            }
        )
        binding.rvWishlist.layoutManager = GridLayoutManager(this, 2)
        binding.rvWishlist.adapter = adapter

        viewModel.favoriteItems.observe(this) { list ->
            adapter.submitList(list)
        }
        backToMain()
    }

    private fun backToMain() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
