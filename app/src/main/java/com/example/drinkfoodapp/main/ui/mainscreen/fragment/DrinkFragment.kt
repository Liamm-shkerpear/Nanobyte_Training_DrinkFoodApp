package com.example.drinkfoodapp.main.ui.mainscreen.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkfoodapp.databinding.FragmentDrinkBinding
import com.example.drinkfoodapp.main.models.DrinkItem
import com.example.drinkfoodapp.main.ui.mainscreen.MainViewModel
import com.example.drinkfoodapp.main.ui.mainscreen.adapter.MenuAdapter

class DrinkFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentDrinkBinding? = null
    private val binding get() = _binding!!
    private val menuAdapter by lazy {
        MenuAdapter(
            onItemClick = ::itemClickHandle,
            onEditClick = ::showEditDialog,
            onDeleteClick = ::showDeleteNotification
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            if (item == null) {
                menuAdapter.clearHighlight()
            }
        }
    }

    private fun showEditDialog(item: Any) {
        if (item !is DrinkItem) return
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 10)
        }
        val edtName = EditText(requireContext()).apply {
            setText(item.name)
            hint = "Tên món"
        }
        val edtPrice = EditText(requireContext()).apply {
            setText(item.price.toString())
            hint = "Giá tiền"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        val edtDesc = EditText(requireContext()).apply {
            setText(item.description)
            hint = "Mô tả"
        }
        layout.addView(edtName)
        layout.addView(edtPrice)
        layout.addView(edtDesc)

        AlertDialog.Builder(requireContext())
            .setTitle("Sửa tên món")
            .setView(layout)
            .setPositiveButton("Cập nhật") { _, _ ->
                val newName = edtName.text.toString().trim()
                val newPrice = edtPrice.text.toString().toIntOrNull() ?: 0
                val newDesc = edtDesc.text.toString().trim()

                if (newName.isNotEmpty()) {
                    viewModel.updateItem(item, newName, newPrice, newDesc, isDrink = true)
                }
            }
            .setNegativeButton("Hủy", null).show()
    }

    private fun showDeleteNotification(item: Any) {
        viewModel.deleteItem(item, isDrink = true)
        Toast.makeText(context, "Đã xóa món", Toast.LENGTH_SHORT).show()
    }

    private fun itemClickHandle(item: Any) {
        viewModel.selectItem(item, isDrink = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_IS_DRINK = "is_drink"

        fun newInstance(isDrink: Boolean): DrinkFragment {
            val fragment = DrinkFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_DRINK, isDrink)
            fragment.arguments = args
            return fragment
        }
    }

}
