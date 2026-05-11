package com.example.drinkfoodapp.main.ui.wine

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ActivityWineScreenBinding
import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import com.example.drinkfoodapp.main.di.Injection
import com.example.drinkfoodapp.main.di.WineViewModelFactory
import com.example.drinkfoodapp.main.ui.collection.CollectionActivity
import com.example.drinkfoodapp.main.ui.wine.adapter.ViewSearchAdapter
import com.example.drinkfoodapp.main.ui.wine.adapter.WineAdapter
import com.example.drinkfoodapp.main.ui.winedetails.WineDetailActivity
import com.example.drinkfoodapp.main.utils.AppConstants
import kotlin.jvm.java

class WineScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWineScreenBinding
    private val wineAdapter by lazy {
        WineAdapter(
            onItemClick = ::itemClickHandle,
            onAddClick = { item -> wineViewModel.addCollection(item) })
    }
    private val searchAdapter by lazy {
        WineAdapter(
            onItemClick = ::itemClickHandle,
            onAddClick = { item -> wineViewModel.addCollection(item) })
    }
    private val previewAdapter by lazy {
        WineAdapter(
            onItemClick = ::itemClickHandle,
            onAddClick = { item -> wineViewModel.addCollection(item) })
    }
    private val viewSearchAdapter by lazy { ViewSearchAdapter(onSearchClick = ::exitSearchMode) }
    private val searchConcatAdapter by lazy {
        ConcatAdapter(searchAdapter, viewSearchAdapter, previewAdapter)
    }
    private val wineViewModel: WineViewModel by viewModels {
        WineViewModelFactory(wineRepository = Injection.provideWineRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWineScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        initWineRv()
        observeData()
        initSearch()
        initToolBar()
    }

    private fun initWineRv() {
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(this@WineScreenActivity)
            adapter = wineAdapter
        }
    }

    private fun observeData() {
        wineViewModel.wineItems.asLiveData().observe(this) { listWine ->
            wineAdapter.submitList(listWine)
            previewAdapter.submitList(listWine.take(2))
        }
        wineViewModel.searchItems.asLiveData().observe(this) { listSearch ->
            searchAdapter.submitList(listSearch)
        }
    }

    private fun itemClickHandle(item: WineItem) {
        val intent = Intent(this@WineScreenActivity, WineDetailActivity::class.java).apply {
            putExtra(AppConstants.EXTRA_WINE, item)
        }
        startActivity(intent)
    }

    private fun initSearch() {
        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.rvMain.adapter != searchConcatAdapter) {
                binding.rvMain.adapter = searchConcatAdapter
            }
        }
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            val query = text?.toString()?.trim() ?: ""
            wineViewModel.searchWines(query)
        }
    }

    private fun exitSearchMode() {
        binding.etSearch.clearFocus()
        binding.etSearch.text?.clear()
        binding.rvMain.adapter = wineAdapter
    }

    private fun initToolBar() {
        binding.toolbarWine.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_collection -> {
                    val intent = Intent(this@WineScreenActivity, CollectionActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
