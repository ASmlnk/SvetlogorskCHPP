package com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.fragment

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentLightingAndOtherEditBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LightingAndOtherEditFragment : BaseFragment<FragmentLightingAndOtherEditBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentLightingAndOtherEditBinding {
        return FragmentLightingAndOtherEditBinding.inflate(inflater, container, false)
    }

    private fun showCustomSnackbar(view: View, text: String) {

        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view
        val background: Drawable? = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.background_snakbar
        )
        snackbarView.background = background

        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.setMargins(0, 100, 0, 0)
        params.gravity = Gravity.TOP
        snackbarView.layoutParams = params

        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.apply {
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.floatingActionButton
                )
            )  // Цвет текста
            textSize = 18f
        }
        snackbar.show()
    }
}