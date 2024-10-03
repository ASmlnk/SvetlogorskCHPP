package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__data.HardData
import com.example.svetlogorskchpp.__domain.en.PermissionRequestWork
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.adapter.StringAutoCompleteAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.factory.ShiftScheduleRequestWorkViewModelFactory
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.DateTimeUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.NoteRequestWorkStateUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.Toast
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.viewModel.ShiftScheduleRequestWorkViewModel
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleRequestWorkBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShiftScheduleRequestWorkBinding.inflate(inflater, container, false)
        adapterAccessions = StringAutoCompleteAdapter(requireContext(), hardData.accessions)
        adapterReason = StringAutoCompleteAdapter(requireContext(), hardData.reasons)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_shiftScheduleRequestWorkFragment_to_shiftScheduleFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

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
                saveEditText()
                dateTimePicker(binding.bTimeOpen)
            }
            bTimeClosed.setOnClickListener {
                saveEditText()
                dateTimePicker(binding.bTimeClosed)
            }
            bSave.setOnClickListener {
                saveEditText()
                viewModel.insertNoteRequestWork()
            }
            bExtend.setOnClickListener {
                saveEditText()
                viewModel.isExtendView(true)
            }

            bTimeOpenExtend.setOnClickListener {
                saveEditText()
                dateTimePicker(binding.bTimeOpenExtend)
            }

            bTimeClosedExtend.setOnClickListener {
                saveEditText()
                dateTimePicker(binding.bTimeClosedExtend)
            }

            bCloseExtend.setOnClickListener {
                saveEditText()
                viewModel.isExtendView(false)
            }

            bOkExtend.setOnClickListener {
                saveEditText()
                viewModel.insertExtendRequestWork(etNumberRequestExtend.text.toString())
            }

            chDispatcher.setOnCheckedChangeListener { _, isChecked ->
                saveEditText()
                viewModel.chipPermission(if (isChecked) PermissionRequestWork.DISPATCHER else PermissionRequestWork.OTHER)
            }

            chChiefEnginee.setOnCheckedChangeListener { _, isChecked ->
                saveEditText()
                viewModel.chipPermission(if (isChecked) PermissionRequestWork.CHIEF_ENGINEER else PermissionRequestWork.OTHER)
            }

            etNameAccession.setAdapter(adapterAccessions)
            etReason.setAdapter(adapterReason)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.noteRequestWorkStateUI.collect { noteRequestWorkStateUI ->
                        visibleExtend(noteRequestWorkStateUI.isExtend)
                        textToUI(noteRequestWorkStateUI)
                        noteRequestWorkStateUI.apply {
                            toastText?.let {
                                showCustomSnackbar(root, it)
                            }

                            setupChipUI(noteRequestWorkUI.permission ?: PermissionRequestWork.OTHER)

                            resetRequestWorkUI?.let {
                                resetText()
                            }
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

    private fun FragmentShiftScheduleRequestWorkBinding.saveEditText() {
        viewModel.saveEditTextUI(
            textAccession = etNameAccession.text.toString(),
            textReason = etReason.text.toString(),
            textAdditionally = etAdditionally.text.toString(),
            textNumber = etNumberRequest.text.toString()
        )
    }

    private fun FragmentShiftScheduleRequestWorkBinding.visibleExtend(isVisible: Boolean) {
        layoutExtend.isGone = !isVisible
        bSave.isGone = isVisible
        bExtend.isGone = isVisible
        resetExtend(!isVisible)
    }

    private fun FragmentShiftScheduleRequestWorkBinding.resetExtend(isReset: Boolean) {
        if (isReset) {
            etNumberRequestExtend.setText(RESET_EDIT_TEXT)
            bTimeOpenExtend.text = resources.getString(R.string.time_open_request_work)
            bTimeClosedExtend.text = resources.getString(R.string.time_closed_request_work)
        }
    }

    private fun FragmentShiftScheduleRequestWorkBinding.resetText() {
        etNameAccession.setText(RESET_EDIT_TEXT)
        etNumberRequestExtend.setText(RESET_EDIT_TEXT)
        etAdditionally.setText(RESET_EDIT_TEXT)
        etReason.setText(RESET_EDIT_TEXT)
        etNumberRequest.setText(RESET_EDIT_TEXT)
        bTimeOpen.text = resources.getString(R.string.time_open_request_work)
        bTimeClosed.text = resources.getString(R.string.time_closed_request_work)
    }

    private fun FragmentShiftScheduleRequestWorkBinding.setupChipUI(permissionRequestWork: PermissionRequestWork) {
        when (permissionRequestWork) {
            PermissionRequestWork.DISPATCHER -> chDispatcher.isChecked = true
            PermissionRequestWork.CHIEF_ENGINEER -> chChiefEnginee.isChecked = true
            PermissionRequestWork.OTHER -> {
                chDispatcher.isChecked = false
                chChiefEnginee.isChecked = false
            }
        }
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

    @SuppressLint("InflateParams")
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
                            binding.bTimeOpen -> viewModel.dateOpen(
                                newCalendar,
                                DateTimeUI.OPEN_TIME
                            )

                            binding.bTimeClosed -> viewModel.dateOpen(
                                newCalendar,
                                DateTimeUI.CLOSE_TIME
                            )

                            binding.bTimeOpenExtend -> viewModel.dateOpen(
                                newCalendar,
                                DateTimeUI.OPEN_EXTEND_TIME
                            )

                            binding.bTimeClosedExtend -> viewModel.dateOpen(
                                newCalendar,
                                DateTimeUI.CLOSE_EXTEND_TIME
                            )
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

    private fun FragmentShiftScheduleRequestWorkBinding.textToUI(
        noteRequestWorkStateUI: NoteRequestWorkStateUI,
    ) {
        noteRequestWorkStateUI.apply {
            textDateOpen?.let {
                bTimeOpen.text = it
            }
            textDateClose?.let {
                bTimeClosed.text = it
            }
            textDateOpenExtend?.let {
                bTimeOpenExtend.text = it
            }
            textDateCloseExtend?.let {
                bTimeClosedExtend.text = it
            }
            etNumberRequest.setText(noteRequestWorkUI.numberRequestWork)
            etReason.setText(noteRequestWorkUI.reason)
            etNameAccession.setText(noteRequestWorkUI.accession)
            etAdditionally.setText(noteRequestWorkUI.additionally)
        }
    }

    private fun showCustomSnackbar(view: View, toast: Toast) {

        val snackbar = Snackbar.make(view, toast.textToast, Snackbar.LENGTH_LONG)

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

    companion object {
        private const val RESET_EDIT_TEXT = ""
    }

}