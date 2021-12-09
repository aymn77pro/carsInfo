package com.example.carsinfo

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carsinfo.data.Cars
import com.example.carsinfo.databinding.FragmentDetilseBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DetilseFragment : Fragment() {
    private val navigationArgs: DetilseFragmentArgs by navArgs()
    lateinit var cars:Cars
    private val viewModel : CarsViewModel by activityViewModels{
        CarsViewModelFactory(
            (activity?.application as CarsInfoApp).database.carsDao()
        )
    }
    private var _binding:FragmentDetilseBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetilseBinding.inflate(inflater,container,false)
        return binding?.root
    }
private fun bind(cars: Cars){
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
        viewModel.retrieveCarsInfo(id).observe(this.viewLifecycleOwner){slected ->
         cars = slected
            bind(cars)
        }
        binding?.delet?.setOnClickListener {
            showConfirmationDialog()

        }
    }
    private fun editCarInfo(){
        val action = DetilseFragmentDirections.actionDetilseFragmentToCarsInfoFragment(cars.id)
       this.findNavController().navigate(action)
    }
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

    private fun deleteCarsInfo(){
        viewModel.delete(cars)
        findNavController().navigateUp()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}