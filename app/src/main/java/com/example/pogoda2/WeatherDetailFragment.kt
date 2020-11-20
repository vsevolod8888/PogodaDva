package com.example.pogoda2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogoda2.databinding.WeatherDetailFragmentBinding


class WeatherDetailFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherDetailFragment()
    }

    private lateinit var viewModel: WeatherDetailViewModel
    private lateinit var mainnviewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<WeatherDetailFragmentBinding>(inflater,
            R.layout.weather_detail_fragment,container,false)

        viewModel = ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
        mainnviewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        mainnviewModel.choosendetail.observe(viewLifecycleOwner, Observer {
            binding.weatherdetail = it
        })
        return binding.root
    }
}

