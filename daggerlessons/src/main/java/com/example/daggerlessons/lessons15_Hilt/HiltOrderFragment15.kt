package com.example.daggerlessons.lessons15_Hilt

import android.content.Context
import androidx.fragment.app.Fragment

open class HiltOrderFragment15: Fragment() {

    lateinit var fragmentComponent: HiltFragmentComponent15

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent = (context as IActivityComponent15).activityComponent().getFragmentComponent()
        fragmentComponent.injectOrderFragment(this as OrderFragment15)

    }
}