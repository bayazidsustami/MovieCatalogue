package com.the_b.moviecatalogue.ui.features.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.ui.features.settings.notification.DailyNotification
import com.the_b.moviecatalogue.ui.features.settings.notification.ReleaseNotification

class MyPreference: PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener,
    Preference.OnPreferenceChangeListener {

    private lateinit var checkDaily: CheckBoxPreference
    private lateinit var checkRelease: CheckBoxPreference

    private var dailyNotification = DailyNotification()
    private var releaseNotification = ReleaseNotification()

    companion object{
        const val DAILY_KEY = "daily_preference"
        const val RELEASE_KEY = "release_preference"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preference)

        init()
        setSummary()
    }

    private fun init(){

        checkDaily = findPreference<CheckBoxPreference>(DAILY_KEY) as CheckBoxPreference
        checkRelease = findPreference<CheckBoxPreference>(RELEASE_KEY) as CheckBoxPreference

        checkDaily.onPreferenceChangeListener = this
        checkRelease.onPreferenceChangeListener = this
    }

    private fun setSummary() {
        val sp = preferenceManager.sharedPreferences

        checkDaily.isChecked = sp.getBoolean(DAILY_KEY, false)
        checkRelease.isChecked = sp.getBoolean(RELEASE_KEY, false)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == DAILY_KEY){
            checkDaily.isChecked = sharedPreferences.getBoolean(DAILY_KEY, false)
        } else if (key == RELEASE_KEY){
            checkRelease.isChecked = sharedPreferences.getBoolean(RELEASE_KEY, false)
        }
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        val key = preference.key
        val value = newValue as Boolean

        when(key){
            DAILY_KEY -> {
                if (value){
                    dailyNotification.setRepeatingAlarm(context!!)
                } else {
                    dailyNotification.cancelAlarm(context!!)
                }
            }
            RELEASE_KEY -> {
                if (value){
                    releaseNotification.setRepeatingAlarm(context!!)
                } else {
                    releaseNotification.cancelAlarm(context!!)
                }
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}