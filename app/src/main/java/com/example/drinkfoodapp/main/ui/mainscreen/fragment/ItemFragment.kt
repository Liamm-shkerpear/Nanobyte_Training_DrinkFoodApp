package com.example.drinkfoodapp.main.ui.mainscreen.fragment

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkfoodapp.databinding.FragmentItemBinding
import com.example.drinkfoodapp.main.model.MenuItem
import com.example.drinkfoodapp.main.ui.mainscreen.MainViewModel
import com.example.drinkfoodapp.main.ui.mainscreen.adapter.MenuAdapter

class ItemFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuAdapter: MenuAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isDrink = arguments?.getBoolean(ARG_IS_DRINK) ?: true

        //Config recyclerview
        menuAdapter = MenuAdapter(
            itemList = emptyList(),
            onEditClick = { item ->
                val editText = android.widget.EditText(requireContext())
                editText.setText(item.name)

                AlertDialog.Builder(requireContext())
                    .setTitle("Sửa tên món")
                    .setView(editText)
                    .setPositiveButton("Cập nhật") { _, _ ->
                        val newName = editText.text.toString().trim()
                        if (newName.isNotEmpty()) {
                            viewModel.updateItem(item, newName, isDrink)
                        }
                    }
                    .setNegativeButton("Hủy", null).show()
            },
            onDeleteClick = { item ->
                viewModel.deleteItem(item, isDrink)
                Toast.makeText(context, "Đã xóa món", Toast.LENGTH_SHORT).show()
            }
        )
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = menuAdapter

        // Quan sát live
        if (isDrink) {
            viewModel.drinkList.observe(viewLifecycleOwner) { list ->
                menuAdapter.updateData(list)
            }
        } else {
            viewModel.foodList.observe(viewLifecycleOwner) { list ->
                menuAdapter.updateData(list)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_IS_DRINK = "is_drink"

        fun newInstance(isDrink: Boolean): ItemFragment {
            val fragment = ItemFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_DRINK, isDrink)
            fragment.arguments = args
            return fragment
        }
    }

}
