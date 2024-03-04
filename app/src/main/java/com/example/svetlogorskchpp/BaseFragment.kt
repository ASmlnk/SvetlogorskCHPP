package com.example.svetlogorskchpp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    var _binding: T? = null

    abstract fun getViewId(): Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DataBindingUtil.setContentView(requireActivity(), getViewId())
        return _binding?.root
    }

     override fun onDestroyView() {
          super.onDestroyView()
          _binding = null
     }

}