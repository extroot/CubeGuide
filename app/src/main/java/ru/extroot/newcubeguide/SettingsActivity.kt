package ru.extroot.newcubeguide

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.preference.*
import ru.extroot.newcubeguide.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity(), Preference.OnPreferenceChangeListener {
    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val settingsFragment = SettingsFragment(this)
        updateFr()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, settingsFragment)
            .commit()
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        Log.i(null ,"123")
        updateFr()
        return true
    }


    private fun updateFr() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<FormulaFragment>(
                R.id.settingsFrLayout, args = bundleOf(
                    "mode" to "pll",
                    "packageName" to packageName,
                    "settingPreview" to true
                )
            )
        }
    }

    class SettingsFragment(val listener: Preference.OnPreferenceChangeListener) : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            val context = preferenceManager.context
            val screen = preferenceManager.createPreferenceScreen(context)
            val algContainerTypeCategory = PreferenceCategory(context)
            algContainerTypeCategory.title = getString(R.string.alg_container_header)
            screen.addPreference(algContainerTypeCategory)

            val gridPreference = SwitchPreferenceCompat(context)
            gridPreference.key = getString(R.string.grid_key)
            gridPreference.switchTextOn = getString(R.string.alg_container_on)
            gridPreference.switchTextOff = getString(R.string.alg_container_off)
            gridPreference.title = getString(R.string.alg_container_type)
            gridPreference.setDefaultValue(R.bool.grid_default_key)
            gridPreference.onPreferenceChangeListener = listener
            algContainerTypeCategory.addPreference(gridPreference)

            val countingPreference = SwitchPreferenceCompat(context)
            countingPreference.key = getString(R.string.counting_key)
            countingPreference.title = getString(R.string.number_switch)
            countingPreference.setDefaultValue(R.bool.counting_default_key)
            countingPreference.onPreferenceChangeListener = listener
            algContainerTypeCategory.addPreference(countingPreference)

            preferenceScreen = screen
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
            return true
        }
    }
}