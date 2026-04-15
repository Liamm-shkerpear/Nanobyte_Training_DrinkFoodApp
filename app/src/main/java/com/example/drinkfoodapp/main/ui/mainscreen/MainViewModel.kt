package com.example.drinkfoodapp.main.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.main.model.MenuItem
import java.util.Locale
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val _drinkList = MutableLiveData<List<MenuItem>>(
        listOf(
            MenuItem("Cà phê đá", R.drawable.ca_fe),
            MenuItem("Trà sữa", R.drawable.tra_sua),
            MenuItem("Nước ép cam", R.drawable.nuoc_cam),
            MenuItem("Sinh tố bơ", R.drawable.sinh_to_bo),
            MenuItem("Nước ép dưa hấu", R.drawable.nc_dua_hau),
            MenuItem("Nước ép táo", R.drawable.nuoc_tao),
            MenuItem("Nước ép dứa", R.drawable.nuoc_dua),
            MenuItem("Capuchino", R.drawable.capuchino),
            MenuItem("Nước ép ổi", R.drawable.nuoc_oi),
            MenuItem("Matcha latte", R.drawable.matcha),
            MenuItem("Trà đào", R.drawable.tra_dao),
            MenuItem("Sữa chua", R.drawable.sua_chua),
        )
    )
    val drinkList: LiveData<List<MenuItem>> get() = _drinkList

    private val _foodList = MutableLiveData<List<MenuItem>>(
        listOf(
            MenuItem("Phở bò", R.drawable.pho),
            MenuItem("Bánh mì", R.drawable.banh_mi),
            MenuItem("Cơm tấm", R.drawable.com_tam),
            MenuItem("Bún chả", R.drawable.bun_cha),
            MenuItem("Bún đậu", R.drawable.bun_dau),
            MenuItem("Bún thịt nướng", R.drawable.bun_thit_nuong),
            MenuItem("Bún riêu", R.drawable.bun_rieu),
            MenuItem("Bánh bèo", R.drawable.banh_beo),
            MenuItem("Bánh canh", R.drawable.banh_canh),
            MenuItem("Hủ tiếu", R.drawable.hu_tieu),
            MenuItem("Mỳ Udon", R.drawable.udon),
            MenuItem("Mỳ quảng", R.drawable.mi_quang),
        )
    )
    val foodList: LiveData<List<MenuItem>> get() = _foodList

    fun addNewItem(name: String, isDrink: Boolean) {
        val defaltImage = if (isDrink) R.drawable.ca_fe else R.drawable.banh_mi
        val newItem = MenuItem(name, defaltImage)

        if (isDrink) {
            val currentList = _drinkList.value?.toMutableList() ?: mutableListOf()
            currentList.add(newItem)
            _drinkList.value = currentList
        } else {
            val currentList = _foodList.value?.toMutableList() ?: mutableListOf()
            currentList.add(newItem)
            _foodList.value = currentList
        }

    }

}
