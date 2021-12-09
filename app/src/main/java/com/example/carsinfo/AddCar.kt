package com.example.carsinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.carsinfo.data.Cars
import com.example.carsinfo.databinding.FragmentCarsInfoBinding
import com.example.carsinfo.databinding.FragmentListCarsBinding


class AddCarsInfoFragment : Fragment() {
//    private val navigationArgs:DetilseFragmentArgs by navArgs()

    private var _binding : FragmentCarsInfoBinding? = null
    private val binding get() = _binding

    private val viewModel:CarsViewModel by activityViewModels{
        CarsViewModelFactory(
            (activity?.application as CarsInfoApp).database.carsDao()
        )
    }
    lateinit var car:Cars

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarsInfoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun isEntryValid(): Boolean {
    return viewModel.isEntryValid(
        binding?.carName?.text.toString(),
        binding?.carType?.text.toString(),
        binding?.carPrice?.text.toString()
    )
    }
    private fun addNewCar(){
        if (isEntryValid()){
            viewModel.addNewCar(
                binding?.carName?.text.toString(),
                binding?.carType?.text.toString(),
                binding?.carPrice?.text.toString()
            )
            
        }
    }

        }


