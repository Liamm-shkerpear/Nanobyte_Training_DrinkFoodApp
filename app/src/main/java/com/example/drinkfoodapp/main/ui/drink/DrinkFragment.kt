package com.example.drinkfoodapp.main.ui.drink

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkfoodapp.databinding.DialogEditItemBinding
import com.example.drinkfoodapp.databinding.FragmentDrinkBinding
import com.example.drinkfoodapp.main.data.domain.entities.MenuItem
import com.example.drinkfoodapp.main.di.Injection
import com.example.drinkfoodapp.main.di.ViewModelFactory
import com.example.drinkfoodapp.main.ui.detail.DetailActivity
import com.example.drinkfoodapp.main.ui.drink.adapter.DrinkMenuAdapter
import com.example.drinkfoodapp.main.ui.home.HomeScreenViewModel
import com.example.drinkfoodapp.main.utils.AppConstants

class DrinkFragment : Fragment() {


    private val viewModel: HomeScreenViewModel by activityViewModels {
        ViewModelFactory(Injection.provideMenuRepository(requireContext()))
    }
    private var _binding: FragmentDrinkBinding? = null
    private val binding get() = _binding!!
    private val menuAdapter by lazy {
        DrinkMenuAdapter(
            onItemClick = ::itemClickHandle,
            onEditClick = ::showEditDialog,
            onDeleteClick = ::showDeleteNotification,
            onFavoriteClick = ::showFavoriteNotification
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = menuAdapter
        }
    }

    private fun initViewModel() {
        viewModel.drinkItems.asLiveData().observe(viewLifecycleOwner) { list ->
            menuAdapter.submitList(list)
        }
    }

    private fun showEditDialog(item: MenuItem) {
        val dialogBinding = DialogEditItemBinding.inflate(layoutInflater)
        dialogBinding.apply {
            edtName.setText(item.name)
            edtPrice.setText(item.price.toString())
            edtDesc.setText(item.description)

            AlertDialog.Builder(requireContext())
                .setTitle("Sửa tên món")
                .setView(dialogBinding.root)
                .setPositiveButton("Cập nhật") { _, _ ->
                    val newName = edtName.text.toString().trim()
                    val newPrice = edtPrice.text.toString().toLongOrNull() ?: 0
                    val newDesc = edtDesc.text.toString().trim()
                    if (newName.isNotEmpty()) {
                        val updateItem = item.copy(name = newName, price = newPrice, description = newDesc)
                        viewModel.saveNewItem(updateItem)
                    }
                }
                .setNegativeButton("Hủy", null).show()
        }
    }

    private fun showDeleteNotification(item: MenuItem) {
        viewModel.deleteItem(item)
        Toast.makeText(context, "Đã xóa món", Toast.LENGTH_SHORT).show()
    }

    private fun itemClickHandle(item: MenuItem) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(AppConstants.EXTRA_MENU_ITEM, item)
        }
        startActivity(intent)
    }

    private fun showFavoriteNotification(item: MenuItem) {
        viewModel.handleFavorite(item)
        Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
