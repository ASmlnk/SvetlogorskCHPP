package com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal

import com.example.svetlogorskchpp.data.model.ShiftPersonalDto
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.domain.en.JobTitle
import com.example.svetlogorskchpp.domain.usecases.JobTitleUseCases
import com.example.svetlogorskchpp.domain.usecases.ShiftUseCases
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.JobTitlePersonal
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.ShiftPersonal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShiftScheduleShiftPersonalInteractorImpl @Inject constructor(
    private val shiftPersonalRepository: ShiftPersonalRepository,
    private val shiftUseCases: ShiftUseCases,
    private val jobTitleUseCases: JobTitleUseCases,
) : ShiftScheduleShiftPersonalInteractor {

    private val jobTitles = listOf(JobTitle.NSS, JobTitle.NSE, JobTitle.DEM_5R, JobTitle.DEM_6R)

    override fun getShiftPersonalStream(): Flow<List<ShiftPersonal>>  =
        shiftPersonalRepository.getShiftPersonalStream().map { shiftPersonalDto ->
            shiftPersonalDto.map {
                ShiftPersonal(
                    shift = shiftUseCases.stringToShift(it.shift),
                    jobTitle = jobTitleUseCases.stringToJobTitle(it.jobTitle),
                    namePersonal = it.namePersonal
                )
            }
        }

    override suspend fun setShiftPersonalBD(list: List<ShiftPersonal>) {
        shiftPersonalRepository.setShiftPersonalBD(
            list.map { ShiftPersonalDto(
                shift = shiftUseCases.shiftToString(it.shift),
                jobTitle = jobTitleUseCases.jobTitleToString(it.jobTitle),
                namePersonal = it.namePersonal
            ) }
        )
    }

    private fun toJobTitlePersonal(listPersonalDto: List<ShiftPersonalDto>) : List<JobTitlePersonal> {
        val jobTitlesPersonals = mutableListOf<JobTitlePersonal>()

        for(i in jobTitles) {


        }
        return jobTitlesPersonals
    }

}