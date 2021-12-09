package com.example.carsinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carsinfo.data.Cars
import com.example.carsinfo.databinding.FragmentAddCarBinding
import com.example.carsinfo.databinding.FragmentDetilseBinding


class AddCarsInfoFragment : Fragment() {
    private val navigationArgs:AddCarsInfoFragmentArgs by navArgs()

    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding

    private val viewModel: CarsViewModel by activityViewModels {
        CarsViewModelFactory(
            (activity?.application as CarsInfoApp).database.carsDao()
        )
    }
    lateinit var car: Cars

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if(id>0){
            viewModel.retrieveCarsInfo(id).observe(this.viewLifecycleOwner){selected ->
              car = selected
              bind(car)
            }
        }else{
            binding?.saveAction?.setOnClickListener {
                addNewCar()
            }
        }
    }
    private fun updateCarInfo(){
        if (isEntryValid()){
            viewModel.updateCarsInfo(
                this.navigationArgs.id,
              this.binding?.carName?.text.toString(),
                this.binding?.carType?.text.toString(),
                this.binding?.carPrice?.text.toString()
                )
            val action = AddCarsInfoFragmentDirections.actionCarsInfoFragmentToListCarsFragment()
            findNavController().navigate(action)
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding?.carName?.text.toString(),
            binding?.carType?.text.toString(),
            binding?.carPrice?.text.toString()
        )
    }
    private fun bind(cars: Cars){
       val price = "%.2f".format(cars.carPrice)
        binding?.apply {
           carName.setText(cars.carName,TextView.BufferType.SPANNABLE)
            carType.setText(cars.carType,TextView.BufferType.SPANNABLE)
            carPrice.setText(cars.carPrice.toString(),TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateCarInfo() }
        }
    }

    private fun addNewCar() {
        if (isEntryValid()) {
            viewModel.addNewCar(
                binding?.carName?.text.toString(),
                binding?.carType?.text.toString(),
                binding?.carPrice?.text.toString()
            )
        }
        val action = AddCarsInfoFragmentDirections.actionCarsInfoFragmentToListCarsFragment()
        findNavController().navigate(action)
    }

}


