package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.list.view_model.ElMotorChapterViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElMotorChapter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ElMotorChapterViewModelFactory {
    fun create(@Assisted elMotorChapter: ElMotorChapter) : ElMotorChapterViewModel
}