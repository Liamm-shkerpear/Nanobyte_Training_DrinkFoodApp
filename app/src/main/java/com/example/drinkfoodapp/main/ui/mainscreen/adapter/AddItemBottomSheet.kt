package com.example.drinkfoodapp.main.ui.mainscreen.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.main.ui.mainscreen.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class AddItemBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_add_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edtName = view.findViewById<TextInputEditText>(R.id.edtFoodName)
        val rgCategory = view.findViewById<RadioGroup>(R.id.rgCategory)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập tên món!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isDrink = rgCategory.checkedRadioButtonId == R.id.rbDrink

            viewModel.addNewItem(name, isDrink)

            Toast.makeText(context, "Đã thêm thành công!", Toast.LENGTH_SHORT).show()

            dismiss()
        }
    }
}
