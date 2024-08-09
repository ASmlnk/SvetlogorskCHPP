package com.example.svetlogorskchpp.inspectionSchedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentChecklistInspectionBinding
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ValveFragment : Fragment() {

  private var _binding: FragmentChecklistInspectionBinding? = null
   private val binding get() = _binding!!

   private lateinit var adapterValve: ChecklistInspectionCheckAdapter
   private val viewModel: ValveViewModel by viewModels()

   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {
       _binding = FragmentChecklistInspectionBinding.inflate(inflater, container, false)

       adapterValve = ChecklistInspectionCheckAdapter(InSc.VALVE.get)


       binding.apply {
           recycleChecklist.adapter = adapterValve

           textView8.text = getString(R.string.valve)
       }

       return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       lifecycleScope.launch {
           viewModel.data.listValveSateFlow.collect {

               adapterValve.submitList(it.sortedBy { it.numberChecklist.toInt() })
               binding.progress.isGone = it.isNotEmpty()
           }
       }
   }

}