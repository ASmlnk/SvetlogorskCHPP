package com.example.svetlogorskchpp.__widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ShiftScheduleWidgetConfigureBinding
import com.example.svetlogorskchpp.__di.Widget
import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
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
    @Inject
    lateinit var shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor

    //  private var shift = Shift.NO_SHIFT

    private lateinit var binding: ShiftScheduleWidgetConfigureBinding

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        setResult(RESULT_CANCELED)

        binding = ShiftScheduleWidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            chipShiftA.setOnCheckedChangeListener { _, b ->
                // if (b) shift = Shift.A_SHIFT
                selectShiftChip(b, "A")
            }
            chipShiftB.setOnCheckedChangeListener { _, b ->
                // if (b) shift = Shift.B_SHIFT
                selectShiftChip(b, "B")
            }
            chipShiftC.setOnCheckedChangeListener { _, b ->
                //  if (b) shift = Shift.C_SHIFT
                selectShiftChip(b, "C")
            }
            chipShiftD.setOnCheckedChangeListener { _, b ->
                // if (b) shift = Shift.D_SHIFT
                selectShiftChip(b, "D")
            }

            chipGroupViewShift.apply {
                check(chipViewAllShift.id)
                setOnCheckedChangeListener { group, checkedId ->
                    if (checkedId == View.NO_ID) {
                        chipGroupViewShift.check(chipViewAllShift.id)
                    }
                }
            }

            chipViewAllShift.setOnCheckedChangeListener { _, b ->
                if (b) lifecycleScope.launch {
                    shiftScheduleCalendarInteractor.setSelectCalendarView("1")
                }
            }
            chipViewOneShift.setOnCheckedChangeListener { _, b ->
                if (b) lifecycleScope.launch {
                    shiftScheduleCalendarInteractor.setSelectCalendarView("2")
                }
            }


            buttonClose.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }

            saveSetting.setOnClickListener {
                lifecycleScope.launch {
                    //   shiftScheduleCalendarInteractor.setSelectShiftSchedule(shift.nameBD)
                }
                val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val isFirstCreation = !sharedPreferences.contains(PREF_PREFIX_KEY + appWidgetId)
                if (isFirstCreation) {
                    sharedPreferences.edit().putBoolean(PREF_PREFIX_KEY + appWidgetId, true).apply()
                    val resultIntent = Intent().apply {
                        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                        action = "ACTION_UPDATE_WIDGET"
                    }
                    setResult(RESULT_OK, resultIntent)
                } else {
                    val updateIntent = Intent(
                        this@ShiftScheduleWidgetConfigureActivity,
                        ShiftScheduleWidget::class.java
                    ).apply {
                        action = "ACTION_UPDATE_WIDGET"
                    }
                    sendBroadcast(updateIntent)
                }
                finish()
            }


            lifecycleScope.launch {
                shiftScheduleCalendarInteractor.getDaysFullCalendarStream().collect { state ->
                    isCheckedChipViewCalendar(state.calendarView)
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

    private fun selectShiftChip(isChecked: Boolean, shift: String) {
        val isAnyChipChecked =
            binding.chipGroupShift.children.filterIsInstance<Chip>().any { it.isChecked }
        lifecycleScope.launch {
            if (isChecked) shiftScheduleCalendarInteractor.setSelectShiftSchedule(shift) else if (!isAnyChipChecked) shiftScheduleCalendarInteractor.setSelectShiftSchedule(
                ""
            )
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

    private fun isCheckedChipViewCalendar(view: String) {
        when (view) {
            "1" -> binding.chipViewAllShift.isChecked = true
            "2" -> binding.chipViewOneShift.isChecked = true
            else -> Any()
        }
    }
}

private const val PREFS_NAME = "com.example.svetlogorskchpp.ShiftScheduleWidget"
private const val PREF_PREFIX_KEY = "appwidget_"

// Write the prefix to the SharedPreferences object for this widget
internal fun saveTitlePref(context: Context, appWidgetId: Int, text: String) {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
    prefs.putString(PREF_PREFIX_KEY + appWidgetId, text)
    prefs.apply()
}

// Read the prefix from the SharedPreferences object for this widget.
// If there is no preference saved, get the default from a resource
internal fun loadTitlePref(context: Context, appWidgetId: Int): String {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0)
    val titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null)
    return titleValue ?: context.getString(R.string.appwidget_text)
}

internal fun deleteTitlePref(context: Context, appWidgetId: Int) {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
    prefs.remove(PREF_PREFIX_KEY + appWidgetId)
    prefs.apply()
}