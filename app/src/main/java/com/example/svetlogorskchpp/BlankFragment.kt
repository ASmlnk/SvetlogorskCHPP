package com.example.svetlogorskchpp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.svetlogorskchpp.databinding.FragmentBlankBinding

class BlankFragment : BaseFragment<FragmentBlankBinding>() {


    private val viewModel: BlankFragmentViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentBlankBinding {
        return FragmentBlankBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGet.setOnClickListener {
            //viewModel.get()
        }
        binding.buttonSet.setOnClickListener {
            //viewModel.setNewElectricalAssembly()
            //viewModel.setNewEm()
        }

        binding.buttonCreateEm.setOnClickListener {
            binding.apply {
                buttonResetEm.isVisible = true
                buttonKaTgLayout.isVisible = true
                buttonOtherLayout.isVisible = true
                textElectricMotor.isVisible = true
            }
            textEM()
        }

        binding.buttonResetEm.setOnClickListener {
            binding.apply {
                buttonResetEm.isVisible = false
                buttonKaTgLayout.isVisible = false
                buttonOtherLayout.isVisible = false
                textElectricMotor.isVisible = false
            }
        }

        binding.buttonKa.setOnClickListener {
            addElementFirebase("Котлоагрегаты")
        }
        binding.buttonTg.setOnClickListener {
            addElementFirebase("Турбогенераторы")
        }
        binding.buttonOther.setOnClickListener {
            addElementFirebase("Остальное")
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.textListSize.text = it.toString()
        }
    }

    private fun addElementFirebase(nameChapter: String) {
        val dataElement = HashMap<String, Any>()
        dataElement["name"] = binding.nameElectricMotor.text.toString()
        dataElement["category"] = binding.category.text.toString()
        dataElement["schemaState"] = binding.schemaState.isChecked
        dataElement["characteristics"] = binding.characteristics.text.toString()
        dataElement["rep"] = binding.rep.isChecked
        dataElement["responsibly"] = binding.responsibly.isChecked
        dataElement["voltage"] = binding.voltage.text.toString()
        dataElement["generalCategory"] = binding.generalCategory.text.toString()
        dataElement["indexSection"] = binding.indexSection.text.toString()
        dataElement["schema"] = binding.schema.text.toString()
        dataElement["p"] = binding.p.text.toString()
        dataElement["i"] = binding.i.text.toString()
        dataElement["pump"] = binding.pump.text.toString()

        viewModel.setNewElement(nameChapter, dataElement)
    }

    fun textEM() {
        val e1 = binding.nameElectricMotor.text.toString()
        val e2 = binding.category.text.toString()
        val e3 = binding.characteristics.text.toString()
        val e4 = binding.voltage.text.toString()
        val e5 = binding.generalCategory.text.toString()
        val e6 = binding.indexSection.text.toString()
        val e7 = binding.schema.text.toString()
        val e8 = binding.p.text.toString()
        val e9 = binding.i.text.toString()
        val e10 = binding.pump.text.toString()

        binding.textElectricMotor.text = requireContext().getString(R.string.text_create_em, e1, e2, e3,e4,e5,e6,e7,e8,e9,e10)
    }

}


