package com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal

import com.example.svetlogorskchpp.data.model.ShiftPersonalDto
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.domain.en.JobTitle
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.domain.usecases.FilterUseCases
import com.example.svetlogorskchpp.domain.usecases.JobTitleUseCases
import com.example.svetlogorskchpp.domain.usecases.ShiftUseCases
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.JobTitlePersonal
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.ShiftPersonal
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.Staff
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShiftScheduleShiftPersonalInteractorImpl @Inject constructor(
    private val shiftPersonalRepository: ShiftPersonalRepository,
    private val shiftUseCases: ShiftUseCases,
    private val jobTitleUseCases: JobTitleUseCases,
    private val filterUseCases: FilterUseCases,
) : ShiftScheduleShiftPersonalInteractor {

    private val jobTitles = listOf(JobTitle.NSS, JobTitle.NSE, JobTitle.DEM_6R, JobTitle.DEM_5R)

    override fun getShiftPersonalStream(): Flow<List<JobTitlePersonal>> =
        shiftPersonalRepository.getShiftPersonalStream().map { shiftPersonalDto ->
            val shiftPersonals = shiftPersonalDto.map {
                ShiftPersonal(
                    shift = shiftUseCases.stringToShift(it.shift),
                    jobTitle = jobTitleUseCases.stringToJobTitle(it.jobTitle),
                    namePersonal = it.namePersonal
                )
            }
            toJobTitlePersonal(shiftPersonals)
        }

    override fun getStaffStream(): Flow<List<Staff>> =
        shiftPersonalRepository.getShiftPersonalStream().map { shiftPersonalDto ->
            shiftPersonalDto.map {
                Staff(
                    shift = shiftUseCases.stringToShift(it.shift),
                    name = it.namePersonal
                )
            }
        }

    override suspend fun setShiftPersonalBD(jobTitlePersonals: List<JobTitlePersonal>) {
        val shiftPersonals = toShiftPersonal(jobTitlePersonals)

        shiftPersonalRepository.setShiftPersonalBD(
            shiftPersonals.map {
                ShiftPersonalDto(
                    shift = it.shift.nameBD,
                    jobTitle = it.jobTitle.nameBD,
                    namePersonal = it.namePersonal
                )
            }
        )
    }

    private fun toJobTitlePersonal(listPersonal: List<ShiftPersonal>): List<JobTitlePersonal> {
        val jobTitlesPersonals = mutableListOf<JobTitlePersonal>()

        for (jobTitle in jobTitles) {
            val jobTitlePersonal = JobTitlePersonal(
                jobTitle = jobTitle,
                namePersonalShiftA = filterUseCases.filterPersonalShiftForJobTitlePersonal(
                    listPersonal,
                    Shift.A_SHIFT,
                    jobTitle
                ),
                namePersonalShiftB = filterUseCases.filterPersonalShiftForJobTitlePersonal(
                    listPersonal,
                    Shift.B_SHIFT,
                    jobTitle
                ),
                namePersonalShiftC = filterUseCases.filterPersonalShiftForJobTitlePersonal(
                    listPersonal,
                    Shift.C_SHIFT,
                    jobTitle
                ),
                namePersonalShiftD = filterUseCases.filterPersonalShiftForJobTitlePersonal(
                    listPersonal,
                    Shift.D_SHIFT,
                    jobTitle
                ),
                namePersonalShiftE = filterUseCases.filterPersonalShiftForJobTitlePersonal(
                    listPersonal,
                    Shift.E_SHIFT,
                    jobTitle
                ),
            )
            jobTitlesPersonals.add(jobTitlePersonal)
        }
        return jobTitlesPersonals
    }

    private fun toShiftPersonal(jobTitlesPersonals: List<JobTitlePersonal>): List<ShiftPersonal> {

        val shiftPersonals = mutableListOf<ShiftPersonal>()

        for (jobTitlePersonal in jobTitlesPersonals) {
            val properties =
                JobTitlePersonal::class.members.filterIsInstance<kotlin.reflect.KProperty1<JobTitlePersonal, *>>()

            for (property in properties) {
                val value = property.get(jobTitlePersonal)
                if (value !is JobTitle) {
                    val nameProperty = property.name
                    when (nameProperty) {
                        "namePersonalShiftA" -> shiftPersonals.add(
                            ShiftPersonal(
                                Shift.A_SHIFT, jobTitlePersonal.jobTitle, value.toString()
                            )
                        )

                        "namePersonalShiftB" -> shiftPersonals.add(
                            ShiftPersonal(
                                Shift.B_SHIFT, jobTitlePersonal.jobTitle, value.toString()
                            )
                        )

                        "namePersonalShiftD" -> shiftPersonals.add(
                            ShiftPersonal(
                                Shift.D_SHIFT, jobTitlePersonal.jobTitle, value.toString()
                            )
                        )

                        "namePersonalShiftC" -> shiftPersonals.add(
                            ShiftPersonal(
                                Shift.C_SHIFT, jobTitlePersonal.jobTitle, value.toString()
                            )
                        )

                        "namePersonalShiftE" -> shiftPersonals.add(
                            ShiftPersonal(
                                Shift.E_SHIFT, jobTitlePersonal.jobTitle, value.toString()
                            )
                        )
                    }
                }
            }
        }
        return shiftPersonals
    }
}