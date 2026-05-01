package com.example.drinkfoodapp.main.utils.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.drinkfoodapp.databinding.LayoutAddItemBinding
import com.example.drinkfoodapp.main.ui.home.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddItemBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: LayoutAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isDrinkTab = arguments?.getBoolean(ARG_IS_DRINK) ?: true

        handleAddItem(isDrinkTab)
    }

    private fun handleAddItem(isDrinkTab: Boolean) {
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val priceStr = binding.edtPrice.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập tên món!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val price = priceStr.toLongOrNull()?: 0
            viewModel.addNewItem(name = name, price = price, description = description, isDrink = isDrinkTab)
            Toast.makeText(context, "Đã thêm thành công!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_IS_DRINK = "is_drink"

        fun newInstance(isDrink: Boolean): AddItemBottomSheet {
            val bottomSheet = AddItemBottomSheet()
            val args = Bundle()
            args.putBoolean(ARG_IS_DRINK, isDrink)
            bottomSheet.arguments = args
            return bottomSheet
        }
    }
}
