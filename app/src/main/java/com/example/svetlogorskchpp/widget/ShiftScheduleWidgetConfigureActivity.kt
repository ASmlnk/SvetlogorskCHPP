package com.example.svetlogorskchpp.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.databinding.ShiftScheduleWidgetConfigureBinding
import com.example.svetlogorskchpp.di.Widget
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorWidgetImpl
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The configuration screen for the [ShiftScheduleWidget] AppWidget.
 */

@AndroidEntryPoint
class ShiftScheduleWidgetConfigureActivity : AppCompatActivity() {
  //  private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var appWidgetText: EditText

    @Widget
    @Inject  lateinit var shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor


    /*private var onClickListener = View.OnClickListener {
        val context = this@ShiftScheduleWidgetConfigureActivity

        // When the button is clicked, store the string locally
        val widgetText = appWidgetText.text.toString()
        saveTitlePref(context, appWidgetId, widgetText)

        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(context)
        updateAppWidget(context, appWidgetManager, appWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)
        finish()
    }*/
    private lateinit var binding: ShiftScheduleWidgetConfigureBinding

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        val appWidgetId = intent.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID) ?: run {
            finish()
            return
        }

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED)

        binding = ShiftScheduleWidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // appWidgetText = binding.appwidgetText as EditText
        binding.apply {
            chipShiftA.setOnCheckedChangeListener { _, b ->
                selectChip(b, "A")
            }
            chipShiftB.setOnCheckedChangeListener { _, b ->
                selectChip(b, "B")
            }
            chipShiftC.setOnCheckedChangeListener { _, b ->
                selectChip(b, "C")
            }
            chipShiftD.setOnCheckedChangeListener { _, b ->
                selectChip(b, "D")
            }


            saveSetting.setOnClickListener {
               /* val resultIntent = Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                }
                setResult(RESULT_OK, resultIntent)
                finish()*/
                val context = this@ShiftScheduleWidgetConfigureActivity

                // When the button is clicked, store the string locally



                // It is the responsibility of the configuration activity to update the app widget
                val appWidgetManager = AppWidgetManager.getInstance(context)
                updateAppWidget(context, appWidgetManager, appWidgetId)

                // Make sure we pass back the original appWidgetId
                val resultValue = Intent()
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                setResult(RESULT_OK, resultValue)
                finish()
            }               //(onClickListener)

            lifecycleScope.launch {
                shiftScheduleCalendarInteractor.getDaysFullCalendarStream().collect { state ->

                    isCheckedChipShift(state.shiftSelect)
                }
            }

        }

        // Find the widget id from the intent.
       /* val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        appWidgetText.setText(loadTitlePref(this@ShiftScheduleWidgetConfigureActivity, appWidgetId))*/
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