package com.example.sufatura.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.sufatura.R
import com.example.sufatura.databinding.FragmentResultBinding
import com.example.sufatura.presentation.viewmodel.ResultViewModel

class ResultFragment : Fragment() {
    private lateinit var resultFragmentBinding : FragmentResultBinding
    val viewModel : ResultViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        resultFragmentBinding = FragmentResultBinding.inflate(inflater, container, false)

        val customerNumber = arguments?.getString("customerNumber")
        val meterReading = arguments?.getString("meterReading")


        if (customerNumber != null && meterReading != null) {
//            viewModel.calculateBill(meterReading, 0, )
        }
        return resultFragmentBinding.root
    }
}