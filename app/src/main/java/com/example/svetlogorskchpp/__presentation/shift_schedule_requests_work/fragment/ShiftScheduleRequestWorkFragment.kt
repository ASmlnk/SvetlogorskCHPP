package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.fragment

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleRequestWorkBinding

class ShiftScheduleRequestWorkFragment: Fragment() {

    private var _binding: FragmentShiftScheduleRequestWorkBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShiftScheduleRequestWorkBinding.inflate(inflater,container, false)

        binding.apply {
            ivHelpAccession.setOnClickListener {
                messageHelpView(
                    resources.getString(R.string.help_request_work_accession),
                    ivHelpAccession
                )
            }
            ivHelpReason.setOnClickListener {
                messageHelpView(
                    resources.getString(R.string.help_request_work_reason),
                    ivHelpReason
                )
            }
        }



        setupUiHintEditText()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUiHintEditText() {
        binding.apply {
            val hintAccession ="   " + resources.getString(R.string.accession)
            val hintReason ="   " + resources.getString(R.string.reason_request_work)
            val hintAdditionally ="   " + resources.getString(R.string.additionally)

            val drawable = ContextCompat.getDrawable(requireContext(),
                R.drawable.baseline_edit_et
            )
            drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

            val imageSpan = ImageSpan(drawable!!, ImageSpan.ALIGN_BASELINE)

            hintEditText(hintAccession, imageSpan, etNameAccession)
            hintEditText(hintReason, imageSpan, etReason)
            hintEditText(hintAdditionally, imageSpan, etAdditionally)

        }
    }

    private fun hintEditText(textHint: String, imageSpan: ImageSpan, view: EditText) {
        val spannableString = SpannableString(textHint)
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        view.hint = spannableString
    }

    private fun messageHelpView(textHelp: String, view: View) {
        val popupView = layoutInflater.inflate(R.layout.popup_layout_help_request_work, null)
        val popupWindow = PopupWindow(popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        val textView = popupView.rootView.findViewById<TextView>(R.id.textHelp)
        textView.text = textHelp
        popupWindow.showAsDropDown(view, 2, 0)
        popupWindow.isFocusable = true
        popupWindow.update()
    }
}