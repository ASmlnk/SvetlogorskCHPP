package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.fragment

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleAddNotesBinding
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter.NoteAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.viewModel.ShiftScheduleAddNotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class ShiftScheduleAddNotesFragment : Fragment() {

    private var _binding: FragmentShiftScheduleAddNotesBinding? = null
    private val binding
        get() = _binding!!

    private val args: ShiftScheduleAddNotesFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ShiftScheduleAddNotesViewModel.ShiftShiftScheduleAddNotesViewModelFactory
    private val adapter: NoteAdapter = NoteAdapter() { note: Note ->
        viewModel.deleteNote(note)
    }

    private val viewModel: ShiftScheduleAddNotesViewModel by viewModels {
        ShiftScheduleAddNotesViewModel.providesFactory(
            assistedFactory = viewModelFactory,
            date = args.navigateAddNoteArgs.date
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShiftScheduleAddNotesBinding.inflate(inflater, container, false)

        binding.apply {

            constraintEditNote.isGone = true
            //  floatingActionButton.isGone = isView

            tvDate.text = viewModel.calendarDate()
            buttonShift1.apply {
                text = getString(R.string.shift, args.navigateAddNoteArgs.prevNightShift.nameApp)
                setOnClickListener {
                    val action =
                        ShiftScheduleAddNotesFragmentDirections.actionShiftScheduleAddNotesFragmentToShiftScheduleStaffDialog(
                            args.navigateAddNoteArgs.prevNightShift
                        )
                    findNavController().navigate(action)
                }
            }
            buttonShift2.apply {
                text = getString(R.string.shift, args.navigateAddNoteArgs.dayShift.nameApp)
                setOnClickListener {
                    val action =
                        ShiftScheduleAddNotesFragmentDirections.actionShiftScheduleAddNotesFragmentToShiftScheduleStaffDialog(
                            args.navigateAddNoteArgs.dayShift
                        )
                    findNavController().navigate(action)
                }
            }
            buttonShift3.apply {
                text = getString(R.string.shift, args.navigateAddNoteArgs.nextNightShift.nameApp)
                setOnClickListener {
                    val action =
                        ShiftScheduleAddNotesFragmentDirections.actionShiftScheduleAddNotesFragmentToShiftScheduleStaffDialog(
                            args.navigateAddNoteArgs.nextNightShift
                        )
                    findNavController().navigate(action)
                }
            }
            ivShift1.setOnClickListener {
                val action =
                    ShiftScheduleAddNotesFragmentDirections.actionShiftScheduleAddNotesFragmentToDialogCalendarDateFragment(
                        InSc.NIGHT.get, viewModel.calendarDateActual().time
                    )
                findNavController().navigate(action)
            }

            ivShift2.setOnClickListener {
                val action =
                    ShiftScheduleAddNotesFragmentDirections.actionShiftScheduleAddNotesFragmentToDialogCalendarDateFragment(
                        InSc.DAY.get, viewModel.calendarDateActual().time
                    )
                findNavController().navigate(action)
            }

            ivShift3.setOnClickListener {
                val dateNight = viewModel.calendarDateActual()
                dateNight.add(Calendar.DAY_OF_MONTH, 1)
                val action =
                    ShiftScheduleAddNotesFragmentDirections.actionShiftScheduleAddNotesFragmentToDialogCalendarDateFragment(
                        InSc.NIGHT.get, dateNight.time
                    )
                findNavController().navigate(action)
            }
            cbTechnical.isChecked = args.navigateAddNoteArgs.isTechnical

            cbTechnical.setOnCheckedChangeListener { _, isChecked ->
                viewModel.insertIsTechnical(isChecked)
            }
            recyclerViewNotes.adapter = adapter
        }
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewEditNote(false)

        binding.apply {
            ivSaveNotes.setOnClickListener {
                viewEditNote()
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                lifecycleScope.launch {
                    delay(500)
                    viewModel.insertNote(content = etNotesText.text.toString())
                    etNotesText.setText("")
                }
            }

            floatingAddNote.setOnClickListener {
                viewEditNote()
            }
            floatingCloseEditNote.setOnClickListener {
                viewEditNote()
                etNotesText.setText("")
                viewModel.resetTimeNote()
            }

            ivNotesAddTime.setOnClickListener {
                val cal = viewModel.calendarDateActual()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        viewModel.viewTime(cal)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.calendarNoteUiState.collect { calendarNoteUi ->
                    binding.apply {
                        calendarNoteUi.timeNote?.let {
                            tvTimeNotes.text = SimpleDateFormat("HH:mm").format(it.time)
                        }
                        tvTimeNotes.isGone = !calendarNoteUi.isTimeNote
                        adapter.submitList(calendarNoteUi.notes.sortedBy { it.dateNotes }
                            .sortedBy { !it.isTimeNotes }) {
                            recyclerViewNotes.scrollToPosition(0)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.deleteNoteTag()
        _binding = null
    }

    private fun viewEditNote() {
        lifecycleScope.launch {
            binding.apply {
                toggleVisibility(constraintEditNote)
                toggleVisibility(floatingAddNote)
                toggleVisibility(floatingCloseEditNote)
                val transition = ChangeBounds()
                transition.duration = 500
                TransitionManager.beginDelayedTransition(recyclerViewNotes, transition)
            }
        }
    }

    fun toggleVisibility(view: View) {
        if (view.isVisible) {
            // Плавное исчезновение
            view.animate()
                .alpha(0f)
                .setDuration(500) // Длительность анимации
                .withEndAction {
                    view.isGone = true // Установите isGone после завершения анимации
                }
        } else {
            view.isVisible = true // Сначала делаем видимым
            view.alpha = 0f // Начальная альфа
            view.animate()
                .alpha(1f)
                .setDuration(600) // Длительность анимации
        }
    }
}