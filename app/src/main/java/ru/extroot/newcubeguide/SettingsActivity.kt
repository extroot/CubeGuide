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
            algsCategory.title = getString(R.string.algs_settings_header)
            screen.addPreference(algsCategory)

            val replaceRw = SwitchPreferenceCompat(context)
            replaceRw.key = getString(R.string.replace_rw_key)
            replaceRw.title = getString(R.string.replace_rw_switch_title)
            replaceRw.setDefaultValue(resources.getBoolean(R.bool.replace_rw_default_key))
            replaceRw.onPreferenceChangeListener = listener
            algsCategory.addPreference(replaceRw)


            val algContainerTypeCategory = PreferenceCategory(context)
            algContainerTypeCategory.title = getString(R.string.alg_container_header)
            screen.addPreference(algContainerTypeCategory)

            val gridPreference = SwitchPreferenceCompat(context)
            gridPreference.key = getString(R.string.grid_key)
            gridPreference.title = getString(R.string.alg_container_switch_title)
            gridPreference.setDefaultValue(resources.getBoolean(R.bool.grid_default_key))
            gridPreference.onPreferenceChangeListener = listener
            algContainerTypeCategory.addPreference(gridPreference)

            val countingPreference = SwitchPreferenceCompat(context)
            countingPreference.key = getString(R.string.counting_key)
            countingPreference.title = getString(R.string.counting_switch_title)
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