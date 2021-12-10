package com.example.carsinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carsinfo.data.Cars
import com.example.carsinfo.databinding.FragmentDetilseBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DetilseFragment : Fragment() {
   // like addCars only change :what Fragment you on
    private val navigationArgs: DetilseFragmentArgs by navArgs()

    // to help you call list inside this fragment
    lateinit var cars: Cars

    // like addCars NOTHING CHANGED COPY IT
    private val viewModel: CarsViewModel by activityViewModels {
        CarsViewModelFactory(
            (activity?.application as CarsInfoApp).database.carsDao()
        )
    }
    private var _binding: FragmentDetilseBinding? = null
    private val binding get() = _binding

    // nothing new same like any fragment you did before
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetilseBinding.inflate(inflater, container, false)
        return binding?.root
    }
//new way is good way to help you make code easy
    private fun bind(cars: Cars) {
        binding?.carsName?.text = cars.carName
        binding?.carType?.text = cars.carType
        binding?.carPrice?.text = cars.carPrice.toString()
        binding?.edit?.setOnClickListener {
            editCarInfo()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.carId
        viewModel.retrieveCarsInfo(id).observe(this.viewLifecycleOwner) { slected ->
            cars = slected
            bind(cars)
        }
        binding?.delet?.setOnClickListener {
            showConfirmationDialog()

        }
    }

    private fun editCarInfo() {
        val action = DetilseFragmentDirections.actionDetilseFragmentToCarsInfoFragment(cars.id)
        this.findNavController().navigate(action)
    }
// copy
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("delete")
            .setCancelable(false)
            .setNegativeButton("no") { _, _ -> }
            .setPositiveButton("yes") { _, _ ->
                deleteCarsInfo()
            }
            .show()
    }

    private fun deleteCarsInfo() {
        viewModel.delete(cars)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}