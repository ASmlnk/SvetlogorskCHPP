package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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





        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}