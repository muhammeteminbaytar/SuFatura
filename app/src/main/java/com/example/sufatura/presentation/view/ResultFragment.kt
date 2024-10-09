package com.example.sufatura.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sufatura.R
import com.example.sufatura.databinding.FragmentResultBinding
import com.example.sufatura.presentation.adapter.TariffAdapter
import com.example.sufatura.presentation.viewmodel.ResultViewModel

class ResultFragment : Fragment() {
    private lateinit var resultFragmentBinding: FragmentResultBinding
    val viewModel: ResultViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        resultFragmentBinding = FragmentResultBinding.inflate(inflater, container, false)

        val customerNumber = arguments?.getString("customerNumber")
        val meterReading = arguments?.getInt("meterReading")


        viewModel.getAllTariffs()

        viewModel.tariffsLiveData.observe(viewLifecycleOwner) { tariffs ->
            if (customerNumber != null && meterReading != null) {
                resultFragmentBinding.txtTutar.text =
                    viewModel.calculateBill(meterReading, 0, tariffs).totalCost.toString()
                resultFragmentBinding.txtHane.text = customerNumber
            }
        }

        resultFragmentBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        resultFragmentBinding.btnSave.setOnClickListener {
            val totalCost = resultFragmentBinding.txtTutar.text.toString().toDouble()

            if (customerNumber != null && meterReading != null) {
                viewModel.saveBill(customerNumber, meterReading, totalCost)
                findNavController().popBackStack()
            }
        }

        return resultFragmentBinding.root
    }
}