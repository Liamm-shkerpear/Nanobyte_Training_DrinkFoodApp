package com.example.drinkfoodapp.main.ui.food

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkfoodapp.databinding.DialogEditItemBinding
import com.example.drinkfoodapp.databinding.FragmentFoodBinding
import com.example.drinkfoodapp.main.models.FoodItem
import com.example.drinkfoodapp.main.ui.detail.DetailActivity
import com.example.drinkfoodapp.main.ui.home.MainViewModel
import com.example.drinkfoodapp.main.ui.food.adapter.FoodMenuAdapter

class FoodFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!
    private val menuAdapter by lazy {
        FoodMenuAdapter(
            onItemClick = ::itemClickHandle,
            onEditClick = ::showEditDialog,
            onDeleteClick = ::showDeleteNotification,
            onFavoriteClick = viewModel::handleFavorite
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
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
        viewModel.foodItem.observe(viewLifecycleOwner) { list ->
            menuAdapter.submitList(list)
        }
    }

    private fun showEditDialog(item: FoodItem) {
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
                        viewModel.updateItem(item, newName, newPrice, newDesc, isDrink = false)
                    }
                }
                .setNegativeButton("Hủy", null).show()
        }
    }

    private fun showDeleteNotification(item: FoodItem) {
        viewModel.deleteItem(item, isDrink = false)
        Toast.makeText(context, "Đã xóa món", Toast.LENGTH_SHORT).show()
    }

    private fun itemClickHandle(item: FoodItem) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("EXTRA_FOOD", item)
            putExtra("IS_DRINK", false)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
