package com.example.svetlogorskchpp.inspectionSchedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentChecklistInspectionBinding
import com.example.svetlogorskchpp.databinding.FragmentInspectionScheduleCalendarBinding
import com.example.svetlogorskchpp.electricalAssembly.ElectricalAssemblyFragmentDirections
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChecklistInspectionFragment : Fragment() {

    @Inject lateinit var data: FirestoreRepository

    private var _binding: FragmentChecklistInspectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModelFactory: CheckListInspectionViewModelFactory
    private lateinit var viewModel: ChecklistInspectionViewModel

    private val adapter = ChecklistInspectionAdapter{
        viewModel.onClickedDialogChecklist(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChecklistInspectionBinding.inflate(inflater, container, false)
        val args = ChecklistInspectionFragmentArgs.fromBundle(requireArguments()).nameCheklist
        viewModelFactory = CheckListInspectionViewModelFactory(args, data)
        viewModel = ViewModelProvider (this, viewModelFactory) [ChecklistInspectionViewModel::class.java]

        binding.apply {
            recycleChecklist.adapter = adapter


            textView8.text = when(args) {
                InSc.INSPECTION.get -> getString(R.string.inspection_schedule_inspection)
                else -> ""
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.data.listChecklistInspection.collect {
                val list = it.map { it.nameBlank }.toSet().toList().sortedBy { it }
                adapter.submitList(list)
                binding.progress.isGone = list.isNotEmpty()
            }
        }

        viewModel.navigateToDialogChecklist.observe(viewLifecycleOwner) {
            it?.let {
                val action = ChecklistInspectionFragmentDirections.actionChecklistInspectionFragmentToDialogChecklistInspection(it)
                this.findNavController().navigate(action)
                viewModel.onDialogNavigation()
            }
        }
    }

}