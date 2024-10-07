package com.example.svetlogorskchpp.__presentation.shift_schedule_list_notes.fragment

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter.NoteAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule_list_notes.viewModel.ShiftScheduleNotesListViewModel
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleNotesListBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShiftScheduleNotesListFragment: Fragment() {

    private val viewModel: ShiftScheduleNotesListViewModel by viewModels()
    private var _binding: FragmentShiftScheduleNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShiftScheduleNotesListBinding.inflate(inflater, container, false)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_shiftScheduleNotesListFragment_to_shiftScheduleFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        adapter = NoteAdapter(
            viewModel.dateLong(),
            onClickDelete = { note: Note ->
                viewModel.deleteNote(note)
            },
            onClickEdit = { note: Note ->
                val noteJson = Gson().toJson(note)
                val action = ShiftScheduleNotesListFragmentDirections.actionShiftScheduleNotesListFragmentToShiftScheduleRequestWorkFragment(noteJson)
                findNavController().navigate(action)
            }
        )
        binding.apply {
            rvNotes.adapter = adapter
            ivAddNote.setOnClickListener {
                val action = ShiftScheduleNotesListFragmentDirections.actionShiftScheduleNotesListFragmentToShiftScheduleRequestWorkFragment("")
                findNavController().navigate(action)
            }
            ivSorted.setOnClickListener {
                findNavController().navigate(R.id.action_shiftScheduleNotesListFragment_to_requestWorkSortedDialog)
            }
            ivFilter.setOnClickListener {
                findNavController().navigate(R.id.action_shiftScheduleNotesListFragment_to_requestWorkFilterDialog)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notesListStateUI.collect { notesListStateUI ->
                    if (notesListStateUI.notes.isNotEmpty()) adapter.submitList(
                        notesListStateUI.notes.sortedByDescending {
                            when (it) {
                                is Note.NoteMy -> it.dateNotes
                                is Note.NoteRequestWork -> it.dateOpen
                            }
                        }
                    )
                    binding.apply {
                        tvDate.text = notesListStateUI.todayDate
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}