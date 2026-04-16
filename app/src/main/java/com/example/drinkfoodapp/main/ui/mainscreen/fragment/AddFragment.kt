package com.example.drinkfoodapp.main.ui.mainscreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.drinkfoodapp.databinding.FragmentAddBinding
import com.example.drinkfoodapp.main.ui.mainscreen.MainViewModel

class AddFragment : Fragment() {
    // Get viewModel
    private val viewModel : MainViewModel by activityViewModels()
    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            val name = binding.edtFoodName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(context, "Chưa nhập món kìa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val isDrink = binding.rbDrink.isChecked
            viewModel.addNewItem(name, isDrink)
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
