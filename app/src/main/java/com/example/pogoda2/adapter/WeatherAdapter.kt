package com.example.pogoda2.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pogoda2.MainViewModel
import com.example.pogoda2.R
import com.example.pogoda2.databinding.WeatherViewHolderBinding
import com.example.pogoda2.repozitory_and_dataanswer.ForViewModelWeather
import kotlinx.android.synthetic.main.weather_view_holder.view.*


class WeatherAdapter(val clickListener: WeatherListener) :
    ListAdapter<ForViewModelWeather, WeatherAdapter.WeatherHolder>(SleepNightDiffCallback()) {
    var selectedItemPosition:Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val item = getItem(position)
        if (position == selectedItemPosition) {
            holder.itemView.constraintLayoutHolder.setBackgroundColor(Color.WHITE)
        }else{
            holder.itemView.constraintLayoutHolder.setBackgroundColor(holder.itemView.context.resources.getColor(
                R.color.primaryColor))
        }
        holder.itemView.setOnClickListener {

            clickListener.onClick(item)
            selectItemPosition(position)
        }
        holder.bind(
            item

        )
    }
    fun selectItemPosition(itemPos:Int){
        selectedItemPosition = itemPos
        notifyDataSetChanged()
    }

    class WeatherHolder(val binding: WeatherViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //var fff:ConstraintLayout

        // @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(
            item: ForViewModelWeather

            ) {
            binding.weather = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WeatherHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeatherViewHolderBinding.inflate(layoutInflater, parent, false)
                return WeatherHolder(binding)
            }
        }

    }
}


class SleepNightDiffCallback : DiffUtil.ItemCallback<ForViewModelWeather>() {
    override fun areItemsTheSame(
        oldItem: ForViewModelWeather,
        newItem: ForViewModelWeather
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ForViewModelWeather,
        newItem: ForViewModelWeather
    ): Boolean {
        return oldItem == newItem
    }
}

class WeatherListener(val clickListener: (itemDetail: ForViewModelWeather) -> Unit) {        // ???????????
    fun onClick(itemDetail: ForViewModelWeather) = clickListener(itemDetail)
}



