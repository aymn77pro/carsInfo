package com.example.carsinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carsinfo.data.Cars
import com.example.carsinfo.databinding.FragmentAddCarBinding


class AddCarsInfoFragment : Fragment() {
    //to take in argement id
    private val navigationArgs: AddCarsInfoFragmentArgs by navArgs()

    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding
// this is how to call viewModel with parameter
    private val viewModel: CarsViewModel by activityViewModels {
        CarsViewModelFactory(
            (activity?.application as CarsInfoApp).database.carsDao()
        )
    }
    // it's sample way to call list
    lateinit var car: Cars

    //don't change it
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return _binding?.root
    }
// here we will change depend on your app
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    // give id argument
    val id = navigationArgs.id

    // here if id is not 0 then make new list item and id is bigger then 0 take id and fetch the info
        if (id > 0) {
            viewModel.retrieveCarsInfo(id).observe(this.viewLifecycleOwner) { selected ->
                car = selected
                bind(car)
            }
        }
        else {
            // id = 0
            binding?.saveAction?.setOnClickListener {
                addNewCar()
            }
        }
    }

    private fun updateCarInfo() {
        if (isEntryValid()) {
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
// make sure user put all date and don't forget any thing
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding?.carName?.text.toString(),
            binding?.carType?.text.toString(),
            binding?.carPrice?.text.toString()
        )
    }
//
    private fun bind(cars: Cars) {
      // to give price nice format
        val price = "%.2f".format(cars.carPrice)
        binding?.apply {
            carName.setText(cars.carName, TextView.BufferType.SPANNABLE)
            carType.setText(cars.carType, TextView.BufferType.SPANNABLE)
            carPrice.setText(price, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateCarInfo() }
        }
    }
// here i made a fun add new item
    private fun addNewCar() {
    // if user put all info
        if (isEntryValid()) {
            viewModel.addNewCar(
                binding?.carName?.text.toString(),
                binding?.carType?.text.toString(),
                binding?.carPrice?.text.toString()
            )
            val action = AddCarsInfoFragmentDirections.actionCarsInfoFragmentToListCarsFragment()
            findNavController().navigate(action)
        } else {
            // if user forget to put all info
            Toast.makeText(context, "some information is missing", Toast.LENGTH_LONG).show()
        }

    }

}


