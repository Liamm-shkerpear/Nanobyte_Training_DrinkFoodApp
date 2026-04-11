package com.example.drinkfoodapp.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.drinkfoodapp.databinding.FragmentItemBinding
import com.example.drinkfoodapp.main.model.MenuItem
import com.example.drinkfoodapp.main.viewmodel.MainViewModel

/**
 * Fragment responsible for displaying either a drink or food item with animation.
 */
class ItemFragment : Fragment() {

    // Companion object đặt ở đầu
    companion object {
        private const val ARG_IS_DRINK = "is_drink"
        private const val ANIMATION_DURATION = 150L

        fun newInstance(isDrink: Boolean): ItemFragment {
            val fragment = ItemFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_DRINK, isDrink)
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: MainViewModel by activityViewModels()

    // Khởi tạo View Binding
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!


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
            viewModel.currentDrink.observe(viewLifecycleOwner) {
                animateAndChangeData(it)  // Dùng it thay cho menuItem để cho gọn
            }
        } else {
            viewModel.currentFood.observe(viewLifecycleOwner) {
                animateAndChangeData(it)
            }
        }

        // Handle reload button
        binding.btnReload.setOnClickListener {
            viewModel.randomizeItems()
        }
    }

    private fun animateAndChangeData(menuItem: MenuItem) {
        // Dùng scope function (apply) để tránh lặp lại binding nhiều lần -> DRY
        binding.apply {
            // BƯỚC 1: Cho mờ đi (alpha = 0) và thu nhỏ lại (scale = 0.8) trong 150 milliseconds
            ivItemImage.animate().alpha(0f).scaleX(0.8f).scaleY(0.8f).setDuration(ANIMATION_DURATION).start()
            tvItemName.animate().alpha(0f).scaleX(0.8f).scaleY(0.8f).setDuration(ANIMATION_DURATION)
                .withEndAction {

                    // BƯỚC 2: Khi hiệu ứng mờ kết thúc (withEndAction), ta tiến hành thay đổi Data
                    ivItemImage.setImageResource(menuItem.imageResId)
                    tvItemName.text = menuItem.name

                    // BƯỚC 3: Thay data xong, cho hiện rõ lại (alpha = 1) và kích thước bình thường (scale = 1)
                    ivItemImage.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(ANIMATION_DURATION)
                        .start()
                    tvItemName.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(ANIMATION_DURATION)
                        .start()

                }.start()
        }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null // Giải phóng binding để tránh memory leak
        }
    }
