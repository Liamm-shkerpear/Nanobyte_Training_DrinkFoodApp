package com.example.drinkfoodapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.drinkfoodapp.databinding.FragmentItemBinding
import com.example.drinkfoodapp.ui.viewmodel.MainViewModel

class ItemFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    // Khởi tạo View Binding an toàn cho Fragment
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

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

        // Quan sát LiveData và cập nhật giao diện
        if (isDrink) {
            viewModel.currentDrink.observe(viewLifecycleOwner) { drinkName ->
                binding.tvItemName.text = drinkName
            }
        } else {
            viewModel.currentFood.observe(viewLifecycleOwner) { foodName ->
                binding.tvItemName.text = foodName
            }
        }

        // Handle reload button
        binding.btnReload.setOnClickListener {
            viewModel.randomizeItems()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Giải phóng binding để tránh memory leak
    }
}