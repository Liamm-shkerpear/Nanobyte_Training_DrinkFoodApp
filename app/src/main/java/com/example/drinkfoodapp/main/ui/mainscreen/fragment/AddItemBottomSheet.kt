package com.example.drinkfoodapp.main.ui.mainscreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.LayoutAddItemBinding
import com.example.drinkfoodapp.main.ui.mainscreen.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

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


        binding.btnSave.setOnClickListener {
            val name = binding.edtFoodName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập tên món!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            viewModel.addNewItem(name, isDrinkTab)

            Toast.makeText(context, "Đã thêm thành công!", Toast.LENGTH_SHORT).show()

            dismiss()
        }
    }

    companion object {
        private const val ARG_IS_DRINK = "is_drink"

        fun newInstance(isDrink: Boolean) : AddItemBottomSheet {
            val bottomSheet = AddItemBottomSheet()
            val args = Bundle()
            args.putBoolean(ARG_IS_DRINK, isDrink)
            bottomSheet.arguments = args
            return bottomSheet
        }
    }
}
