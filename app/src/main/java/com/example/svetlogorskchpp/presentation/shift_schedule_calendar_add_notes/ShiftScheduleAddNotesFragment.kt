package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleAddNotesBinding

class ShiftScheduleAddNotesFragment: Fragment() {

    private var _binding: FragmentShiftScheduleAddNotesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShiftScheduleAddNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}