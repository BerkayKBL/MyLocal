package com.berkaykbl.mylocal

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.berkaykbl.mylocal.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentLocale: String
    private var selectedCalendar: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentLocale = Locale.getDefault().toLanguageTag()
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val locale = prefs.getString("app_locale", "")

        if (locale != null) {
            setLocale(locale)
        }

        if (savedInstanceState != null && savedInstanceState.containsKey("selectedDate")) {
            selectedCalendar = Calendar.getInstance().apply {
                timeInMillis = savedInstanceState.getLong("selectedDate")
            }
            changeDate()
        }

        binding.changeLocale.setOnClickListener {
            when (currentLocale) {
                "tr" -> setLocale("en")
                "en" -> setLocale("de")
                "de" -> setLocale("tr")
                else -> setLocale("en")
            }
        }

        binding.selectDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    selectedCalendar = Calendar.getInstance()
                    selectedCalendar!!.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    changeDate()
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    fun changeDate() {

        val dateFormat = SimpleDateFormat("d MMMM yyyy, EEEE", Locale(currentLocale))
        val formattedDate = dateFormat.format(selectedCalendar!!.time)

        binding.date.text = formattedDate
    }

    fun setLocale(locale: String) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale)
        )
        Locale.setDefault(Locale(locale))

        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
        sharedPref.putString("app_locale", locale)
        sharedPref.apply() // Save last language to app_locale with SharedPreferences
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedCalendar?.let { outState.putSerializable("selectedDate", it.timeInMillis) }
    }
}