package com.example.pogoda2

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class CityNameDialogFragment : DialogFragment() {
private lateinit var viewModelMain: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragment_enter_city_name, container, false)
        val enterName: EditText = rootView.findViewById(R.id.edit_text_city_name)
        val btnSearch: ImageButton = rootView.findViewById(R.id.button_enter_city_name)
        viewModelMain = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        btnSearch.setOnClickListener(){
            viewModelMain.refreshDataFromRepository2(enterName.text.toString())
            dismiss()
        }
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog:Dialog = super.onCreateDialog(savedInstanceState)
        val window: Window = dialog.getWindow()!!
        val wlp: WindowManager.LayoutParams = window.getAttributes()

        wlp.gravity = Gravity.TOP
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.setAttributes(wlp)
        return dialog
    }

}