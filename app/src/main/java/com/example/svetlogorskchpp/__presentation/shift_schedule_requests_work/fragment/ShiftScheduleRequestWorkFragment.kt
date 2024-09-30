package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavArgs
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__data.HardData
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.fragment.ShiftScheduleAddNotesFragmentDirections
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.viewModel.ShiftScheduleAddNotesViewModel
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.adapter.StringAutoCompleteAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.factory.ShiftScheduleRequestWorkViewModelFactory
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.DateTimeUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.NoteRequestWorkStateUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.ToastRequestWork
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.viewModel.ShiftScheduleRequestWorkViewModel
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleRequestWorkBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class ShiftScheduleRequestWorkFragment : Fragment() {

    private var _binding: FragmentShiftScheduleRequestWorkBinding? = null
    private val binding
        get() = _binding!!

    private val args: ShiftScheduleRequestWorkFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ShiftScheduleRequestWorkViewModelFactory

    private val viewModel: ShiftScheduleRequestWorkViewModel by viewModels {
        ShiftScheduleRequestWorkViewModel.providesFactory(
            assistedFactory = viewModelFactory,
            noteRequestWork = args.noteRequestWork
        )
    }

    private lateinit var adapterAccessions: StringAutoCompleteAdapter
    private lateinit var adapterReason: StringAutoCompleteAdapter
    private val hardData = HardData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentShiftScheduleRequestWorkBinding.inflate(inflater, container, false)
        adapterAccessions = StringAutoCompleteAdapter(requireContext(), hardData.accessions)
        adapterReason = StringAutoCompleteAdapter(requireContext(), hardData.reasons)

        val callback = object : OnBackPressedCallback(true ) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_shiftScheduleRequestWorkFragment_to_shiftScheduleFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, // LifecycleOwner
            callback
        )

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
            bSave.setOnClickListener {
                viewModel.insertNoteRequestWork(
                    textAccession = etNameAccession.text.toString(),
                    textReason = etReason.text.toString(),
                    textAdditionally = etAdditionally.text.toString(),
                    textNumber = etNumberRequest.text.toString()
                )
            }
            etNameAccession.setAdapter(adapterAccessions)
            etReason.setAdapter(adapterReason)


            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.noteRequestWorkStateUI.collect { noteRequestWorkStateUI ->
                        visibleExtend(noteRequestWorkStateUI.isExtend)
                        textToButtonTime(noteRequestWorkStateUI)
                        noteRequestWorkStateUI.toastText?.let {
                            showCustomSnackbar(root, it)
                        }
                    }
                }
            }
        }



        setupUiHintEditText()
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentShiftScheduleRequestWorkBinding.visibleExtend(isVisible: Boolean) {
            layoutExtend.isGone = !isVisible
    }

    private fun setupUiHintEditText() {
        binding.apply {
            val hintAccession = "   " + resources.getString(R.string.accession)
            val hintReason = "   " + resources.getString(R.string.reason_request_work)
            val hintAdditionally = "   " + resources.getString(R.string.additionally)

            val drawable = ContextCompat.getDrawable(
                requireContext(),
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
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val textView = popupView.rootView.findViewById<TextView>(R.id.textHelp)
        textView.text = textHelp
        popupWindow.showAsDropDown(view, 2, 0)
        popupWindow.isFocusable = true
        popupWindow.update()
    }

    private fun dateTimePicker(buttonTime: Button) {
        val calendar = viewModel.calendarInstanceUI()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)

                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        val newCalendar = calendar.clone() as Calendar

                        when (buttonTime) {
                            binding.bTimeOpen -> viewModel.dateOpen(newCalendar, DateTimeUI.OPEN_TIME)
                            binding.bTimeClosed -> viewModel.dateOpen(newCalendar, DateTimeUI.CLOSE_TIME)
                        }
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

    private fun FragmentShiftScheduleRequestWorkBinding.textToButtonTime(
        noteRequestWorkStateUI: NoteRequestWorkStateUI,
    ) {
        noteRequestWorkStateUI.textDateOpen?.let {
            bTimeOpen.text = it
        }
        noteRequestWorkStateUI.textDateClose?.let {
            bTimeClosed.text = it
        }
    }

    private fun showCustomSnackbar(view: View, toastRequestWork: ToastRequestWork) {

        val snackbar = Snackbar.make(view, toastRequestWork.textToast, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view
        val background: Drawable? = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shift_schedule_snackbar_background
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
                    R.color.calendar_background
                )
            )  // Цвет текста
            textSize = 18f
        }
        snackbar.show()
    }

}