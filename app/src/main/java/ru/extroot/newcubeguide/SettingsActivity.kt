package ru.extroot.newcubeguide

import android.os.Bundle
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
        if (preference.key == "change_rw_m") {
            // It checks like if 'smth' in string meh
            // TODO: Find normal way to save MultiSelectListPreference
            val values: CharSequence = newValue.toString()
            with (preference.sharedPreferences!!.edit()) {
                putBoolean("replace_rw", values.contains("replace_rw"))
                putBoolean("replace_lw", values.contains("replace_lw"))
                putBoolean("replace_dw", values.contains("replace_dw"))
                putBoolean("replace_uw", values.contains("replace_uw"))
                apply()
            }
        }
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

            val algsCategory = PreferenceCategory(context)
            algsCategory.title = "Настройки формул"
            screen.addPreference(algsCategory)

            val changeRw = MultiSelectListPreference(context)
            changeRw.title = "Заменять Rw на R"
            changeRw.key = "change_rw_m"
            changeRw.setEntries(R.array.replace_rwr)
            changeRw.setEntryValues(R.array.replace_rwr_values)
            changeRw.onPreferenceChangeListener = listener
            algsCategory.addPreference(changeRw)


            val algContainerTypeCategory = PreferenceCategory(context)
            algContainerTypeCategory.title = getString(R.string.alg_container_header)
            screen.addPreference(algContainerTypeCategory)

            val gridPreference = SwitchPreferenceCompat(context)
            gridPreference.key = getString(R.string.grid_key)
            gridPreference.switchTextOn = getString(R.string.alg_container_on)
            gridPreference.switchTextOff = getString(R.string.alg_container_off)
            gridPreference.title = getString(R.string.alg_container_type)
            gridPreference.setDefaultValue(resources.getBoolean(R.bool.grid_default_key))
            gridPreference.onPreferenceChangeListener = listener
            algContainerTypeCategory.addPreference(gridPreference)

            val countingPreference = SwitchPreferenceCompat(context)
            countingPreference.key = getString(R.string.counting_key)
            countingPreference.title = getString(R.string.number_switch)
            countingPreference.setDefaultValue(resources.getBoolean(R.bool.counting_default_key))
            countingPreference.onPreferenceChangeListener = listener
            algContainerTypeCategory.addPreference(countingPreference)

            preferenceScreen = screen
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
            return true
        }
    }
}