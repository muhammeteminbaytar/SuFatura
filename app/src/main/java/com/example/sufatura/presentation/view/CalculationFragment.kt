package com.example.sufatura.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sufatura.R
import com.example.sufatura.databinding.FragmentCalculationBinding

class CalculationFragment : Fragment() {

    private lateinit var fragmentCalculationBinding: FragmentCalculationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCalculationBinding = FragmentCalculationBinding.inflate(inflater, container, false)

        fragmentCalculationBinding.submitButton.setOnClickListener {
            val customerNumber = fragmentCalculationBinding.customerNumberInput.text.toString()
            val meterReading = fragmentCalculationBinding.meterReadingInput.text.toString()

            if (customerNumber.isBlank() || meterReading.isBlank()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(
                    CalculationFragmentDirections.actionCalculationFragmentToResultFragment(
                        customerNumber,
                        meterReading
                    )
                )
            }
        }

        return fragmentCalculationBinding.root
    }
}
