package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__data.HardData
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.adapter.StringAutoCompleteAdapter
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleRequestWorkBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class ShiftScheduleRequestWorkFragment: Fragment() {

    private var _binding: FragmentShiftScheduleRequestWorkBinding? = null
    private val binding
        get() = _binding!!

    private val calendar = Calendar.getInstance()

    private lateinit var adapterAccessions: StringAutoCompleteAdapter
    private lateinit var adapterReason: StringAutoCompleteAdapter
    private val hardData = HardData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShiftScheduleRequestWorkBinding.inflate(inflater,container, false)
        adapterAccessions = StringAutoCompleteAdapter(requireContext(), hardData.accessions)
        adapterReason = StringAutoCompleteAdapter(requireContext(), hardData.reasons)

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
            bTimeOpen.setOnClickListener {
                dateTimePicker(binding.bTimeOpen)
            }
            bTimeClosed.setOnClickListener {
                dateTimePicker(binding.bTimeClosed)
            }
            etNameAccession.setAdapter(adapterAccessions)
            etReason.setAdapter(adapterReason)


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

    private fun dateTimePicker(buttonTime: Button) {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)

                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)

                        val date = SimpleDateFormat("dd MMMM yyyy HH:mm").format(calendar.time)
                        buttonTime.text = date

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}