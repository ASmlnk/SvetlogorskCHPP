package com.example.visionzero.zeroVision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.visionzero.R
import com.example.visionzero.databinding.DialogZeroVision1Binding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ZeroVisionDialog : BottomSheetDialogFragment() {

    private var _binding: DialogZeroVision1Binding? = null
    private val binding: DialogZeroVision1Binding
        get() = _binding!!

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogZeroVision1Binding.bind(
            inflater.inflate(
                R.layout.dialog_zero_vision_1,
                container
            )
        )

        val indexZeroVision = ZeroVisionDialogArgs.fromBundle(requireArguments()).inexZeroVision

        when (indexZeroVision) {
            "1" -> {
                binding.apply {
                    textNameZeroVision1.text = resources.getString(R.string.zero_vision_1)
                    textNameZeroVision2.text = resources.getString(R.string.zero_vision_11)
                    imageViewZeroVision.setImageResource(R.drawable.zero_vision_1)
                }
            }

            "2" -> {
                binding.apply {
                    textNameZeroVision1.text = resources.getString(R.string.zero_vision_2)
                    textNameZeroVision2.text = resources.getString(R.string.zero_vision_21)
                    imageViewZeroVision.setImageResource(R.drawable.zero_vision_2)
                }
            }

            "3" -> {
                binding.apply {
                    textNameZeroVision1.text = resources.getString(R.string.zero_vision_3)
                    textNameZeroVision2.text = resources.getString(R.string.zero_vision_31)
                    imageViewZeroVision.setImageResource(R.drawable.zero_vision_3)
                }
            }

            "4" -> {
                binding.apply {
                    textNameZeroVision1.text = resources.getString(R.string.zero_vision_4)
                    textNameZeroVision2.text = resources.getString(R.string.zero_vision_41)
                    imageViewZeroVision.setImageResource(R.drawable.zero_vision_4)
                }
            }

            "5" -> {
                binding.apply {
                    textNameZeroVision1.text = resources.getString(R.string.zero_vision_5)
                    textNameZeroVision2.text = resources.getString(R.string.zero_vision_51)
                    imageViewZeroVision.setImageResource(R.drawable.zero_vision_5)
                }
            }

            "6" -> {
                binding.apply {
                    textNameZeroVision1.text = resources.getString(R.string.zero_vision_6)
                    textNameZeroVision2.text = resources.getString(R.string.zero_vision_61)
                    imageViewZeroVision.setImageResource(R.drawable.zero_vision_6)
                }
            }

            "7" -> {
                binding.apply {
                    textNameZeroVision1.text = resources.getString(R.string.zero_vision_7)
                    textNameZeroVision2.text = resources.getString(R.string.zero_vision_71)
                    imageViewZeroVision.setImageResource(R.drawable.zero_vision_7)
                }
            }
        }
        val adapter = ZeroVisionAdapter(indexZeroVision)
        binding.recycleItemZeroVision.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}