package com.example.drinkfoodapp.main.ui.collection

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkfoodapp.databinding.ActivityCollectionBinding
import com.example.drinkfoodapp.main.data.domain.entities.WineItem
import com.example.drinkfoodapp.main.di.Injection
import com.example.drinkfoodapp.main.di.WineViewModelFactory
import com.example.drinkfoodapp.main.ui.wine.WineViewModel
import com.example.drinkfoodapp.main.ui.wine.adapter.WineAdapter
import com.example.drinkfoodapp.main.ui.winedetails.WineDetailActivity
import com.example.drinkfoodapp.main.utils.AppConstants

class CollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectionBinding
    private val wineViewModel: WineViewModel by viewModels {
        WineViewModelFactory(wineRepository = Injection.provideWineRepository(this))
    }
    private val wineAdapter by lazy {
        WineAdapter(
            onItemClick = ::itemClickHandle,
            onAddClick = { item -> wineViewModel.addCollection(item) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWineRv()
        observeViewModel()
        onBtnBackClick()
    }

    private fun itemClickHandle(item: WineItem) {
        val intent = Intent(this@CollectionActivity, WineDetailActivity::class.java).apply {
            putExtra(AppConstants.EXTRA_WINE, item)
        }
        startActivity(intent)
    }

    private fun initWineRv() {
        binding.rvCollection.apply {
            layoutManager = LinearLayoutManager(this@CollectionActivity)
            adapter = wineAdapter
        }
    }

    private fun observeViewModel() {
        wineViewModel.collectionItems.asLiveData().observe(this) { items ->
            wineAdapter.submitList(items)
        }
    }

    private fun onBtnBackClick() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
