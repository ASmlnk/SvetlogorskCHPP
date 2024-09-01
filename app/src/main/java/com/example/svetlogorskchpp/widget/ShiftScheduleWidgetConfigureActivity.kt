package com.example.svetlogorskchpp.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver.PendingResult
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.databinding.ShiftScheduleWidgetConfigureBinding
import com.example.svetlogorskchpp.di.Widget
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The configuration screen for the [ShiftScheduleWidget] AppWidget.
 */

@AndroidEntryPoint
class ShiftScheduleWidgetConfigureActivity : AppCompatActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    @Widget
    @Inject  lateinit var shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor

    private var shift = Shift.NO_SHIFT

    private lateinit var binding: ShiftScheduleWidgetConfigureBinding

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        setResult(RESULT_CANCELED)

        binding = ShiftScheduleWidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            chipShiftA.setOnCheckedChangeListener { _, b ->
                if (b) shift = Shift.A_SHIFT
                //selectChip(b, "A")
            }
            chipShiftB.setOnCheckedChangeListener { _, b ->
                if (b) shift = Shift.B_SHIFT
                //selectChip(b, "B")
            }
            chipShiftC.setOnCheckedChangeListener { _, b ->
                if (b) shift = Shift.C_SHIFT
               // selectChip(b, "C")
            }
            chipShiftD.setOnCheckedChangeListener { _, b ->
                if (b) shift = Shift.D_SHIFT
                //selectChip(b, "D")
            }

            buttonClose.setOnClickListener {
                finish()
            }

            saveSetting.setOnClickListener {
                lifecycleScope.launch {
                    shiftScheduleCalendarInteractor.setSelectShiftSchedule(shift.nameBD)
                }
                val updateIntent = Intent(this@ShiftScheduleWidgetConfigureActivity, ShiftScheduleWidget::class.java).apply {
                    action = "ACTION_UPDATE_WIDGET"
                }
                val resultIntent = Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    action = "ACTION_UPDATE_WIDGET"
                }

                setResult(RESULT_OK, resultIntent)
                sendBroadcast(updateIntent)
                finish()
            }


            lifecycleScope.launch {
                shiftScheduleCalendarInteractor.getDaysFullCalendarStream().collect { state ->

                    isCheckedChipShift(state.shiftSelect)
                }
            }

            appWidgetId = intent?.extras?.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish()
                return
            }
        }
    }
    private fun selectChip(isChecked: Boolean, shift: String) {
        val isAnyChipChecked = binding.chipGroupShift.children.filterIsInstance<Chip>().any { it.isChecked }
        lifecycleScope.launch {
            if (isChecked) shiftScheduleCalendarInteractor.setSelectShiftSchedule( shift ) else if (!isAnyChipChecked) shiftScheduleCalendarInteractor.setSelectShiftSchedule( "" )
        }
    }

    private fun isCheckedChipShift(shift: Shift) {
        when (shift) {
            Shift.A_SHIFT -> binding.chipShiftA.isChecked = true
            Shift.B_SHIFT -> binding.chipShiftB.isChecked = true
            Shift.C_SHIFT -> binding.chipShiftC.isChecked = true
            Shift.D_SHIFT -> binding.chipShiftD.isChecked = true
            Shift.E_SHIFT -> Any()
            Shift.NO_SHIFT -> Any()
        }
    }
}