package com.example.pogoda2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
        val btnSearch: Button = rootView.findViewById(R.id.button_enter_city_name)
        viewModelMain = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        btnSearch.setOnClickListener(){
            viewModelMain.refreshDataFromRepository2(enterName.text.toString())
            dismiss()
        }
        return rootView
    }

}