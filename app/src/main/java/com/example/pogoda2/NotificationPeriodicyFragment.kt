package com.example.pogoda2

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import com.example.pogoda2.foreground_service.ForegroundService


class NotificationPeriodicyFragment : DialogFragment(){
    var sharedPref: SharedPreferences? = null
    val MyPREFERENCES = "MyPrefs"
    val BUTTON_TIME_REPEAT_: String = "button timerepeat"


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(
            R.layout.notification_periodicity_fragment,
            container,
            false
        )
        sharedPref = context?.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        val rg: RadioGroup = rootView.findViewById(R.id.radioGroupp)
        val lastButtonState: Int? = sharedPref?.getInt(BUTTON_TIME_REPEAT_, 1000)
        when (lastButtonState) {
            60000 -> {
                val radBut1: RadioButton = rootView.findViewById(R.id.radioButton1)
                radBut1.isChecked = true
            }
            3600000 -> {
                val radBut2: RadioButton = rootView.findViewById(R.id.radioButton2)
                radBut2.isChecked = true
            }
            36000000 -> {
                val radBut3: RadioButton = rootView.findViewById(R.id.radioButton3)
                radBut3.isChecked = true
            }
        }

        val btnOk: Button = rootView.findViewById(R.id.button2)
        btnOk.setOnClickListener() {
            val selectedId: Int = rg.checkedRadioButtonId
            when (selectedId) {
                R.id.radioButton1 -> {
                    ForegroundService.timeRepeat = 60000

                    with(sharedPref?.edit()) {
                        this?.putInt(BUTTON_TIME_REPEAT_, 60000)
                        this?.apply()
                    }
                }

                R.id.radioButton2 -> {
                    ForegroundService.timeRepeat = 3600000

                    with(sharedPref?.edit()) {
                        this?.putInt(BUTTON_TIME_REPEAT_, 3600000)
                        this?.apply()
                    }
                }
                R.id.radioButton3 -> {
                    ForegroundService.timeRepeat = 36000000
                    with(sharedPref?.edit()) {
                        this?.putInt(BUTTON_TIME_REPEAT_, 36000000)
                        this?.apply()
                    }
                }

            }
            dismiss()
        }

        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        val window: Window = dialog.getWindow()!!
        val wlp: WindowManager.LayoutParams = window.getAttributes()

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.setAttributes(wlp)
        return dialog
    }




}
