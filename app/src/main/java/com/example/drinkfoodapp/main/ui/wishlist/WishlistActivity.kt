package com.example.drinkfoodapp.main.ui.wishlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkfoodapp.databinding.ActivityWishlistBinding
import com.example.drinkfoodapp.main.data.domain.entities.MenuItem
import com.example.drinkfoodapp.main.di.Injection
import com.example.drinkfoodapp.main.di.ViewModelFactory
import com.example.drinkfoodapp.main.ui.detail.DetailActivity
import com.example.drinkfoodapp.main.ui.drink.adapter.DrinkMenuAdapter
import com.example.drinkfoodapp.main.ui.home.HomeScreenViewModel
import com.example.drinkfoodapp.main.utils.AppConstants
import kotlin.jvm.java

class WishlistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishlistBinding
    private val viewModel: HomeScreenViewModel by viewModels {
        ViewModelFactory(Injection.provideMenuRepository(this))
    }
    private val adapter = DrinkMenuAdapter(
        onFavoriteClick = { item ->
            viewModel.handleFavorite(item)
        },
        onItemClick = { item ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(AppConstants.EXTRA_MENU_ITEM, item)
            }
            startActivity(intent)
        }
    )
    private var allFavorite: List<MenuItem> = emptyList()
    private var currentFilter: String = AppConstants.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        backToMain()
        observeViewModel()
        filterItem()
    }

    private fun initView() {
        binding.rvWishlist.layoutManager = GridLayoutManager(this, 2)
        binding.rvWishlist.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.favoriteItems.asLiveData().observe(this) { items ->
            allFavorite = items
            handleFilter()
        }
    }

    private fun backToMain() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun filterItem() {
        binding.cgFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                currentFilter = when (checkedIds.first()) {
                    binding.chipFood.id -> AppConstants.FOOD
                    binding.chipDrink.id -> AppConstants.DRINK
                    else -> AppConstants.ALL
                }
                handleFilter()
            }
        }
    }

    private fun handleFilter() {
        val filterList = if (currentFilter == AppConstants.ALL) {
            allFavorite
        } else {
            allFavorite.filter { item ->
                item.category == currentFilter
            }
        }
        adapter.submitList(filterList)
    }
}
