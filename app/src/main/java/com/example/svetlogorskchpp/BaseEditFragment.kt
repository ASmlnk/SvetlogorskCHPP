package com.example.svetlogorskchpp

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.adapter.ProtectionEditAdapter
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryRzaBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseEditFragment <T : ViewBinding> : BaseFragment<T>() {

    protected lateinit var phaseProtectionAdapter: ProtectionEditAdapter
    protected lateinit var earthProtectionAdapter: ProtectionEditAdapter

   protected fun showCustomSnackbar(view: View, text: String) {

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

    protected fun setupProtectionView(
        binding:  ContentLayoutEditOryRzaBinding,
        onClickPhaseProtection: (protection: String) -> Unit,
        onClickEarthProtection: (protection: String) -> Unit,
        addPhaseProtection: (protection: String) -> Unit,
        addEarthProtection: (protection: String) -> Unit

        ) {
        phaseProtectionAdapter = ProtectionEditAdapter {protection ->
            onClickPhaseProtection(protection)
        }
        earthProtectionAdapter= ProtectionEditAdapter {protection ->
            onClickEarthProtection(protection)
        }
        binding.apply {
            rvPhaseProtection.adapter = phaseProtectionAdapter
            ivPhaseProtection.setOnClickListener {
                val textProtection = etPhaseProtection.text.toString()
                if(textProtection.isNotEmpty()) addPhaseProtection(textProtection)
                etPhaseProtection.setText("")
            }
            rvEarthProtection.adapter = earthProtectionAdapter
            ivEarthProtection.setOnClickListener {
                val textProtection = etEarthProtection.text.toString()
                if(textProtection.isNotEmpty()) addEarthProtection(textProtection)
                etEarthProtection.setText("")
            }
        }
    }
}