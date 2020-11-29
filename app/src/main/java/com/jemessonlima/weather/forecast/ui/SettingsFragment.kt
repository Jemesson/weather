package com.jemessonlima.weather.forecast.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jemessonlima.weather.forecast.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    private lateinit var prefs: SharedPreferences
    private var temperatureUnit = ""
    private var language = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = view.context.getSharedPreferences("shared_settings", 0)

        initSettings()
        pressButtonSave()
    }

    private fun initSettings() {
        temperatureUnit = prefs.getString("temperature_unit", "C").toString()
        language = prefs.getString("language", "EN").toString()

        when (temperatureUnit) {
            "C" -> rbCelsius.isChecked = true
            "F" -> rbFahrenheit.isChecked = true
        }

        when (language) {
            "EN" -> rbEnglishLanguage.isChecked = true
            "PT" -> rbPortugueseLanguage.isChecked = true
        }
    }

    private fun pressButtonSave() {
        rgTemperature.setOnCheckedChangeListener { view, id ->
            val radioButton = view.findViewById<RadioButton>(id)

            if (radioButton.isChecked) {
                when (radioButton.id) {
                    R.id.rbCelsius -> temperatureUnit = "C"
                    R.id.rbFahrenheit -> temperatureUnit = "F"

                }
            }
        }

        rgLanguage.setOnCheckedChangeListener { view, id ->
            val radioButton = view.findViewById<RadioButton>(id)

            if (radioButton.isChecked) {
                when (radioButton.id) {
                    R.id.rbEnglishLanguage -> language = "EN"
                    R.id.rbPortugueseLanguage -> language = "PT"

                }
            }
        }

        btnSettingsSave.setOnClickListener {
            prefs.edit().apply() {
                putString("temperature_unit", temperatureUnit)
                putString("language", language)
                apply()
            }

            Toast.makeText(it.context, R.string.setting_save, Toast.LENGTH_SHORT).show()
        }
    }
}
