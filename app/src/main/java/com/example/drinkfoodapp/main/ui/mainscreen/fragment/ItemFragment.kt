package com.example.drinkfoodapp.main.ui.mainscreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.drinkfoodapp.databinding.FragmentItemBinding
import com.example.drinkfoodapp.main.model.MenuItem
import com.example.drinkfoodapp.main.ui.mainscreen.MainViewModel

class ItemFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()
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

        // Quan sát LiveData và update ui
        if (isDrink) {
            viewModel.currentDrink.observe(viewLifecycleOwner) {
                animateAndChangeData(it)
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
        binding.apply {
            ivItemImage.animate().alpha(0f).scaleX(0.8f).scaleY(0.8f)
                .setDuration(ANIMATION_DURATION).start()
            tvItemName.animate().alpha(0f).scaleX(0.8f).scaleY(0.8f).setDuration(ANIMATION_DURATION)
                .withEndAction {

                    ivItemImage.setImageResource(menuItem.imageResId)
                    tvItemName.text = menuItem.name

                    ivItemImage.animate().alpha(1f).scaleX(1f).scaleY(1f)
                        .setDuration(ANIMATION_DURATION)
                        .start()
                    tvItemName.animate().alpha(1f).scaleX(1f).scaleY(1f)
                        .setDuration(ANIMATION_DURATION)
                        .start()

                }.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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

}
