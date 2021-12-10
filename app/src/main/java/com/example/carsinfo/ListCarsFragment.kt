package com.example.carsinfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsinfo.databinding.FragmentListCarsBinding


class ListCarsFragment : Fragment() {

    private val viewModel: CarsViewModel by activityViewModels {
        CarsViewModelFactory(
            (activity?.application as CarsInfoApp).database.carsDao()
        )
    }

    private var _binding: FragmentListCarsBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListCarsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adabter = CarsListAdapter {
            val action = ListCarsFragmentDirections.actionListCarsFragmentToDetilseFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context)
        binding?.recyclerView?.adapter = adabter
        viewModel.allCarsInfo.observe(this.viewLifecycleOwner) { car ->
            car.let { adabter.submitList(it) }
        }

        binding?.addNote?.setOnClickListener {
            val action = ListCarsFragmentDirections.actionListCarsFragmentToCarsInfoFragment()
            findNavController().navigate(action)
        }
    }

}