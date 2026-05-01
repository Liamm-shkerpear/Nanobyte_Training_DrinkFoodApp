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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkfoodapp.databinding.DialogEditItemBinding
import com.example.drinkfoodapp.databinding.FragmentDrinkBinding
import com.example.drinkfoodapp.main.models.DrinkItem
import com.example.drinkfoodapp.main.ui.detail.DetailActivity
import com.example.drinkfoodapp.main.ui.home.MainViewModel
import com.example.drinkfoodapp.main.ui.drink.adapter.DrinkMenuAdapter

class DrinkFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()
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
        viewModel.drinkItem.observe(viewLifecycleOwner) { list ->
            menuAdapter.submitList(list)
        }
    }

    private fun showEditDialog(item: DrinkItem) {
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
                        viewModel.updateItem(item, newName, newPrice, newDesc, isDrink = true)
                    }
                }
                .setNegativeButton("Hủy", null).show()
        }
    }

    private fun showDeleteNotification(item: DrinkItem) {
        viewModel.deleteItem(item, isDrink = true)
        Toast.makeText(context, "Đã xóa món", Toast.LENGTH_SHORT).show()
    }

    private fun itemClickHandle(item: DrinkItem) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("EXTRA_DRINK", item)
            putExtra("IS_DRINK", true)
        }
        startActivity(intent)
    }

    private fun showFavoriteNotification(item: DrinkItem) {
        viewModel.handleFavorite(item)
        Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
