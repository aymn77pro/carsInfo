package com.example.carsinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carsinfo.data.Cars
import com.example.carsinfo.databinding.CarInfoBinding
import com.example.carsinfo.databinding.FragmentListCarsBinding

class CarsListAdapter(private val onCarInfoClicke:(Cars)->Unit):
ListAdapter<Cars,CarsListAdapter.CarViewHolder>(DiffCallBack) {


class CarViewHolder(private var binding:CarInfoBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(cars: Cars){
        binding.carsName.text = cars.carName
        binding?.carsName.setOnClickListener {
            Toast.makeText(binding.root.context,"${cars.carName}",Toast.LENGTH_LONG).show()
        }
        binding.carType.text = cars.carType
        binding.carPrice.text = cars.carPrice.toString()
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            CarInfoBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
    val current = getItem(position)
        holder.itemView.setOnClickListener {
          onCarInfoClicke(current)
        }
        holder.bind(current)

    }
    companion object{
        private val DiffCallBack = object : DiffUtil.ItemCallback<Cars>(){
            override fun areItemsTheSame(oldItem: Cars, newItem: Cars): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Cars, newItem: Cars): Boolean {
               return oldItem.carName == newItem.carName
            }

        }
    }
}